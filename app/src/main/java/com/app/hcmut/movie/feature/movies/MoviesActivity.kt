package com.app.hcmut.movie.feature.movies

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.viewpager.widget.ViewPager
import com.app.hcmut.movie.R
import com.app.hcmut.movie.ext.gone
import com.app.hcmut.movie.ext.hideKeyboard
import com.app.hcmut.movie.ext.toast
import com.app.hcmut.movie.ext.visible
import com.app.hcmut.movie.feature.movies.adapter.MoviesPagerAdapter
import com.app.hcmut.movie.feature.search.SearchResultActivity
import com.bumptech.glide.Glide
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_movies.*
import kotlinx.android.synthetic.main.content_movies.*
import kotlinx.android.synthetic.main.drawer_menu.*
import java.util.*

class MoviesActivity : AppCompatActivity(), ViewPager.OnPageChangeListener, SignInBottomDialog.IActionListener,
    GoogleApiClient.OnConnectionFailedListener {

    private var lastPage = 0
    private lateinit var googleApiClient: GoogleApiClient
    private var account: GoogleSignInAccount? = null
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var callbackManager: CallbackManager
    lateinit var tvSignOut: AppCompatTextView
    lateinit var tvSignIn: AppCompatTextView

    private var bottomDialog: SignInBottomDialog? = null

    companion object {
        const val GOOGLE_SIGN_IN_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
        tvSignIn = findViewById(R.id.tvSignIn)
        tvSignOut = findViewById(R.id.tvSignOut)
        initView()
        initToolbar()
        initGoogleSignIn()
        initDrawerMenu()
        callbackManager = CallbackManager.Factory.create()

    }

    private fun initToolbar() {
        icDrawer.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        icSearch.setOnClickListener {
            groupSearch.visible()
            groupNormal.gone()
        }
        icClose.setOnClickListener {
            groupSearch.gone()
            groupNormal.visible()
            edtSearch.setText("")
            hideKeyboard()
        }
        tvSearch.setOnClickListener {
            val query = edtSearch.text?.trim()
            if (query?.isEmpty() == true) {
                edtSearch.error = "Please enter keyword"
            } else {
                startActivity(SearchResultActivity.newInstance(this, query.toString()))
            }
        }
    }

    private fun initDrawerMenu() {
        when (account) {
            null -> {
                tvSignIn.visible()
                tvSignOut.gone()
            }
            else -> {
                tvSignIn.gone()
                tvSignOut.visible()
            }
        }
        tvSavedMovie.setOnClickListener {
            signOut()
            toast("saved movie")
        }
        tvSignOut.setOnClickListener {
            signOut()
        }
        tvSignIn.setOnClickListener {
            bottomDialog = SignInBottomDialog.newInstance()
            bottomDialog?.show(supportFragmentManager, bottomDialog?.tag)
        }
    }

    private fun initGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        account = GoogleSignIn.getLastSignedInAccount(this)
//        updateUI(account)
    }

    private fun signInGoogle() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN_REQUEST_CODE)
    }

    private fun signInFacebook() {
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    updateUIFacebook(loginResult.accessToken)
                }

                override fun onCancel() {
                    // App code
                }

                override fun onError(exception: FacebookException) {
                    // App code
                }
            })
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == GOOGLE_SIGN_IN_REQUEST_CODE) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            account = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
            updateUIGoogle(account)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Gray", "signInResult:failed code=" + e.statusCode)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateUIGoogle(account: GoogleSignInAccount?) {
        //TODO apply name, change visible signIn to Sign Out
        tvSignIn.gone()
        tvSignOut.visible()
        if (account?.photoUrl != null) Glide.with(this).load(account.photoUrl).into(imvAvatar)
        val personName = account?.displayName
        val giveName = account?.givenName
        val familyName = account?.familyName
        tvName.text = "Hello, $personName"
    }

    private fun updateUIFacebook(accessToken: AccessToken) {
        val request =
            GraphRequest.newMeRequest(
                accessToken
            ) { `object`, response ->
                //TODO apply name, change visible signIn to Sign Out
                val name = `object`?.getString("name")
                Glide.with(this@MoviesActivity)
                    .load("https://graph.facebook.com/" + `object`?.getString("id") + "/picture?type=large")
                    .into(imvAvatar)
            }
        val parameters = Bundle()
        parameters.putString("fields", "id,name")
        request.parameters = parameters
        request.executeAsync()

    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.d("Gray", p0.errorMessage)
    }

    override fun onClickGoogle() {
        signInGoogle()
        bottomDialog?.dismiss()
    }

    override fun onClickFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(
            this@MoviesActivity,
            Arrays.asList("public_profile, email")
        )
        signInFacebook()
        bottomDialog?.dismiss()
    }

    override fun onStart() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleApiClient = GoogleApiClient.Builder(this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()
        googleApiClient.connect()
        super.onStart()
    }

    private fun initView() {
        val pagerAdapter = MoviesPagerAdapter(supportFragmentManager, this)
        viewPager.offscreenPageLimit = MoviesPagerAdapter.NUM_PAGE
        viewPager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.getTabAt(0)?.customView = pagerAdapter.createTabView(0, true)
        tabLayout.getTabAt(1)?.customView = pagerAdapter.createTabView(1)
        tabLayout.getTabAt(2)?.customView = pagerAdapter.createTabView(2)
        viewPager.addOnPageChangeListener(this)
    }

    override fun onPageScrollStateChanged(p0: Int) {}

    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}

    override fun onPageSelected(position: Int) {
        tabLayout.getTabAt(position)?.customView?.findViewById<TextView>(R.id.tvTabTitle)?.apply {
            setTextColor(ContextCompat.getColor(this@MoviesActivity, R.color.tab_selected))
            typeface = Typeface.DEFAULT_BOLD
            textSize = 20f
        }
        tabLayout.getTabAt(lastPage)?.customView?.findViewById<TextView>(R.id.tvTabTitle)?.apply {
            setTextColor(ContextCompat.getColor(this@MoviesActivity, R.color.tab_unselected))
            typeface = Typeface.DEFAULT
            textSize = 14f
        }
        lastPage = position
    }

    private fun signOut() {
        //TODO apply name, change visible sign Out to Sign In
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback {
            account = null
            tvSignOut.gone()
            tvSignIn.visible()
            tvName.gone()
            Glide.with(this).load(R.color.black).into(imvAvatar)
            Toast.makeText(applicationContext, "Logged Out", Toast.LENGTH_SHORT).show()
        }
        LoginManager.getInstance().logOut()
    }

    fun showLoading() {
    }

    fun hideLoading() {
    }
}