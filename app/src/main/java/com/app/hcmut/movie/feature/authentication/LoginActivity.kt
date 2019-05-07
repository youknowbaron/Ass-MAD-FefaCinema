package com.app.hcmut.movie.feature.authentication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.app.hcmut.movie.R
import com.app.hcmut.movie.databinding.ActivityLoginBinding
import com.app.hcmut.movie.feature.movies.MoviesActivity
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.Task
import java.util.*


class LoginActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var callbackManager: CallbackManager

    companion object {
        const val GOOGLE_SIGN_IN_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        callbackManager = CallbackManager.Factory.create()
        initGoogleSignIn()
        signIn()
    }

    //Sign in with google
    private fun initGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
//        val account = GoogleSignIn.getLastSignedInAccount(this)
//        updateUI(account)
    }

    private fun signIn() {
        binding.btnSignUpEmail.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, GOOGLE_SIGN_IN_REQUEST_CODE)
        }

        binding.btnSignUpFacebook.setOnClickListener {
            //Log in
            LoginManager.getInstance().logInWithReadPermissions(
                this@LoginActivity,
                Arrays.asList("public_profile, email")
            )
        }

        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                var profileTracker: ProfileTracker? = null
                override fun onSuccess(loginResult: LoginResult) {
                    if (Profile.getCurrentProfile() == null) {
                        profileTracker = object : ProfileTracker() {
                            override fun onCurrentProfileChanged(oldProfile: Profile, currentProfile: Profile) {
                                result()
                                Log.d("Gray: ", Profile.getCurrentProfile().name)
                                Log.d("Gray: ", Profile.getCurrentProfile().firstName)
                                Log.d("Gray: ", Profile.getCurrentProfile().lastName)
                                Log.d("Gray: ", Profile.getCurrentProfile().middleName)
                                Log.d(
                                    "Gray picture: ",
                                    Profile.getCurrentProfile().getProfilePictureUri(20, 20).toString()
                                )
                                Log.d("Gray link uri: ", Profile.getCurrentProfile().linkUri.userInfo)

                                startActivity(Intent(this@LoginActivity, MoviesActivity::class.java))
                                profileTracker?.stopTracking()
                            }
                        }
                    } else {
                        Log.d("Gray: ", Profile.getCurrentProfile().name)
                        Log.d("Gray: ", Profile.getCurrentProfile().firstName)
                        Log.d("Gray: ", Profile.getCurrentProfile().lastName)
                        Log.d("Gray: ", Profile.getCurrentProfile().middleName)
                        Log.d("Gray picture: ", Profile.getCurrentProfile().getProfilePictureUri(20, 20).toString())
                        Log.d("Gray link uri: ", Profile.getCurrentProfile().linkUri.userInfo)
                        startActivity(Intent(this@LoginActivity, MoviesActivity::class.java))
                    }

                }

                override fun onCancel() {
                    // App code
                }

                override fun onError(exception: FacebookException) {
                    // App code
                }
            })
    }

    private fun result() {
        val graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken()
        ) { _, response ->
            Log.d("Json", response.jsonObject.toString())
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == GOOGLE_SIGN_IN_REQUEST_CODE) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }

        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
            updateUI(account)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Gray", "signInResult:failed code=" + e.statusCode)
            updateUI(null)
        }
    }

    private fun updateUI(account: GoogleSignInAccount?) {
        val personName = account?.displayName
        val personGivenName = account?.givenName
        val personFamilyName = account?.familyName
        val personEmail = account?.email
        val personPhoto = account?.photoUrl
        startActivity(Intent(this, MoviesActivity::class.java))
        Log.d("Gray", "person name: $personName")
        Log.d("Gray", "person give name: $personGivenName")
        Log.d("Gray", "person family name: $personFamilyName")
        Log.d("Gray", "person email: $personEmail")
        Log.d("Gray", "person photo: $personPhoto")
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.d("Gray", p0.errorMessage)
    }
}