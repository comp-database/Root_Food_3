package com.example.root_food_2

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Button
import com.example.root_food_2.databinding.ActivityHomeScreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import com.squareup.picasso.Picasso
import java.util.*

class HomeScreen : AppCompatActivity(), TextToSpeech.OnInitListener {
    lateinit var binding : ActivityHomeScreenBinding
    private var tts: TextToSpeech? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        binding.Image.setImageURI(intent.extras?.getParcelable("photo"))
//        val email = intent.getStringExtra("email")
//        val displayName = intent.getStringExtra("name")
//        val image = intent.getStringExtra("photo")
//        binding.Name.text = displayName
//        binding.Email.text = email
//        binding.Image.setImageURI(intent.getParcelableExtra("photo"))

        binding.speak.isEnabled = false
        tts = TextToSpeech(this, this)
        binding.speak.setOnClickListener {
            speakOut()
        }

        binding.enter.setOnClickListener {
            startActivity(Intent(this,FoodActivity::class.java))
        }
        FirebaseAuth.getInstance().currentUser?.let { firebaseUser ->
            // if the user is logged in, display their info on the screen
            binding.Name.setText(firebaseUser.displayName)
            binding.Email.setText(firebaseUser.email)
//            etPhone.setText(firebaseUser.phoneNumber)
            Picasso.get().load(firebaseUser.photoUrl).resize(250,250).into(binding.Image)
        }

        binding.translate.setOnClickListener {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(TranslateLanguage.MARATHI)
            .build()
        val englishHindiTranslator = Translation.getClient(options)

        //Downloads the model files required for translation, if they are not already present
        englishHindiTranslator.downloadModelIfNeeded()
            .addOnSuccessListener {
                Log.e(TAG, "Download Successful")
            }
            .addOnFailureListener {
                Log.e(TAG, "Download Error: " + it.printStackTrace().toString())
            }

        //Translates the given input from the source language into the target language.
        englishHindiTranslator.translate(binding.Name.text.toString())
            .addOnSuccessListener {
                //set translated text in textview
                binding.Name.setText(it)
            }
            .addOnFailureListener {
                //Error
                Log.e(TAG, "Error: " + it.localizedMessage.toString())
            }
            getLifecycle().addObserver(englishHindiTranslator)
        }

    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.forLanguageTag("hin-IND"))

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS","The Language not supported!")
            } else {
                binding.speak.isEnabled = true
            }
        }
    }

    private fun speakOut() {
//        val text = binding.Name.text.toString()
        val text = "Tushar yadzhava"
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null,"")
    }

    public override fun onDestroy() {
        // Shutdown TTS when
        // activity is destroyed
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }
}