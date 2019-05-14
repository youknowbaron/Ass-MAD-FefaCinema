package com.app.hcmut.movie.feature.movies

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.viewpager.widget.ViewPager
import com.app.hcmut.movie.R
import com.app.hcmut.movie.ext.*
import com.app.hcmut.movie.feature.BaseActivity
import com.app.hcmut.movie.feature.movies.adapter.MoviesPagerAdapter
import com.app.hcmut.movie.feature.search.SearchResultActivity
import com.app.hcmut.movie.getUserPrefObj
import com.app.hcmut.movie.model.User
import com.app.hcmut.movie.saveUserPrefObj
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
import com.roger.catloadinglibrary.CatLoadingView
import kotlinx.android.synthetic.main.activity_movies.*
import kotlinx.android.synthetic.main.content_movies.*
import kotlinx.android.synthetic.main.drawer_menu.*
import java.util.*

class MoviesActivity : BaseActivity(), ViewPager.OnPageChangeListener, SignInBottomDialog.IActionListener,
    GoogleApiClient.OnConnectionFailedListener {

    private var lastPage = 0
    private lateinit var googleApiClient: GoogleApiClient
    private var account: GoogleSignInAccount? = null
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var callbackManager: CallbackManager
    private var user: User? = null

    private var bottomDialog: SignInBottomDialog? = null
    private lateinit var loadingFragment: CatLoadingView
    private var isLoading = false

    companion object {
        const val GOOGLE_SIGN_IN_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
        user = getUserPrefObj()
        FacebookSdk.sdkInitialize(this.applicationContext)
        loadingFragment = CatLoadingView()
        initGoogleSignIn()
        initToolbar()
        initDrawerMenu()
        initView()
        callbackManager = CallbackManager.Factory.create()
        initSignInFacebook()
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
        when (user) {
            null -> {
                tvName.text = ""
                tvSignIn.text = getString(R.string.sign_in)
                Glide.with(this).load(R.drawable.icon_avatar_empty).into(imvAvatar)
            }
            else -> {
                tvSignIn.text = getString(R.string.sign_out)
                Glide.with(this).load(user?.url).into(imvAvatar)
                tvName.text = getString(R.string.hello_name, user?.name)
            }
        }
        tvSavedMovie.setOnClickListener {
            onClickSaved()
        }
        tvWatchedMovie.setOnClickListener {
            onClickSaved()
        }
        tvSignIn.setOnClickListener {
            if (tvSignIn.text == getString(R.string.sign_in)) {
                bottomDialog = SignInBottomDialog.newInstance()
                bottomDialog?.show(supportFragmentManager, bottomDialog?.tag)
            } else if (tvSignIn.text == getString(R.string.sign_out)) {
                showMessageHasChoose("Sign out", "Are you sure want to sign out?") {
                    signOut()
                }
            }
        }
    }

    private fun onClickSaved() {
        if (user == null) {
            showMessage("Please sign in first")
        } else {
            showMessage("This feature will release soon")
        }
    }

    private fun initGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.server_client_id))
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

    private fun initSignInFacebook() {
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    updateUIFacebook(loginResult.accessToken)
                }

                override fun onCancel() {
                }

                override fun onError(exception: FacebookException) {
                    showMessage("Something went wrong", "Error")
                }
            })
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == GOOGLE_SIGN_IN_REQUEST_CODE) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            account = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
            updateUIGoogle(account)
        } catch (e: ApiException) {
            showMessage("Something went wrong", "Error")
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Gray", "signInResult:failed code=" + e.statusCode)
        }
    }

    private fun updateUIGoogle(account: GoogleSignInAccount?) {
        tvSignIn.text = getString(R.string.sign_out)
        if (account?.photoUrl != null) Glide.with(this).load(account.photoUrl).into(imvAvatar)
        val personName = account?.displayName
        tvName.text = getString(R.string.hello_name, personName)
        user = User(account?.photoUrl.toString(), personName)
        this.saveUserPrefObj(user)
    }

    private fun updateUIFacebook(accessToken: AccessToken) {
        val request =
            GraphRequest.newMeRequest(
                accessToken
            ) { `object`, _ ->
                val name = `object`?.getString("name")
                val url = "https://graph.facebook.com/" + `object`?.getString("id") + "/picture?type=large"
                Glide.with(this@MoviesActivity)
                    .load(url)
                    .into(imvAvatar)
                tvName.text = getString(R.string.hello_name, name)
                tvSignIn.text = getString(R.string.sign_out)
                user = User(url, name)
                this.saveUserPrefObj(user)
            }
        val parameters = Bundle()
        parameters.putString("fields", "id,name")
        request.parameters = parameters
        request.executeAsync()

    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        showMessage("Connection Failed", "Error")
    }

    override fun onClickGoogle() {
        signInGoogle()
        bottomDialog?.dismiss()
    }

    override fun onClickFacebook() {
        if (AccessToken.getCurrentAccessToken() != null) LoginManager.getInstance().logOut()
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"))
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
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback {
            tvSignIn.text = getString(R.string.sign_in)
            tvName.text = ""
            Glide.with(this).load(R.drawable.icon_avatar_empty).into(imvAvatar)
            user = null
            this.saveUserPrefObj(null)
        }
        GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE) {
            LoginManager.getInstance().logOut()
            tvSignIn.text = getString(R.string.sign_in)
            tvName.text = ""
            Glide.with(this).load(R.drawable.icon_avatar_empty).into(imvAvatar)
            user = null
            this.saveUserPrefObj(null)
        }
        showMessage("Signed out successfully")
    }
}