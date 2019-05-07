package com.app.hcmut.movie.feature.authentication

import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import com.app.hcmut.movie.R
import com.app.hcmut.movie.databinding.ActivityLoginBinding
import com.app.hcmut.movie.feature.movies.MoviesActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.Task


class LoginActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    companion object {
        const val GOOGLE_SIGN_IN_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        initGoogleSignIn()
        signInWithGoogleSignIn()
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

    private fun signInWithGoogleSignIn() {
        binding.btnSignUpEmail.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, GOOGLE_SIGN_IN_REQUEST_CODE)
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
        Log.d("Gray",p0.errorMessage)
    }
}