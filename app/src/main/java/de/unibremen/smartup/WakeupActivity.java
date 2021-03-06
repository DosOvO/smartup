package de.unibremen.smartup;

import android.content.Intent;
import android.media.AudioManager;
import android.provider.ContactsContract;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

import de.unibremen.smartup.model.Question;

public class WakeupActivity extends AppCompatActivity {
    TextToSpeech textToSpeech;
    private String answer;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wakeup);
        textView = (TextView)findViewById(R.id.show_question);
        if (AudioPlay.isAudioPlaying) {
            AudioPlay.stopAudio();
        }

        Intent intent = getIntent();
        final String question = intent.getStringExtra("question");
        answer = intent.getStringExtra("answer");
        textView.setText(question);
        initTts(question);
    }

    public void speak(View view) {
        startVoiceRecognitionActivity();
    }

    private void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, R.string.speech_hint);
        startActivityForResult(intent, 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            Log.wtf("Recognized", matches.toString());
            Log.wtf("CORRECT ANSWER", answer);
            String[] answers = answer.split(",");

            if (answers.length == 1) {
                for (String possibleAnswer : matches) {
                    Log.wtf("LOOP1", possibleAnswer.toLowerCase());
                    if (possibleAnswer.toLowerCase().contains(answer)
                            || answer.toLowerCase().contains(possibleAnswer)) {
                        initTts("Korrekte Antwort");
                        Intent intent = new Intent(this, Layout.class);
                        startActivity(intent);
                        return;
                    }
                }
            } else {
                for (String possibleAnswer : matches) {
                    for (String keyword : answers) {
                        Log.wtf("LOOP2", possibleAnswer.toLowerCase());
                        if (possibleAnswer.toLowerCase().contains(keyword)
                                || keyword.toLowerCase().contains(possibleAnswer)) {
                            initTts("Korrekte Antwort");
                            Intent intent = new Intent(this, Layout.class);
                            startActivity(intent);
                            return;
                        }
                    }
                }
            }
            initTts("Falsche Antwort, versuche es noch einmal");
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }

    private void initTts(final String speakThis) {
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.GERMANY);
                    Bundle bundle = new Bundle();
                    bundle.putInt(TextToSpeech.Engine.KEY_PARAM_STREAM, AudioManager.STREAM_MUSIC);
                    textToSpeech.speak(speakThis, TextToSpeech.QUEUE_FLUSH, bundle, null);
                }
            }
        });
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }
}
