package com.example.rfgr.stopwatch;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class StopwatchActivity extends AppCompatActivity {

    private int seconds = 0; //tworzę dwie zmienne prywatne
    private boolean running;
    private boolean wasRunning; //zmienna która przechowuje informacje czy stoper (aktywność) była uruchomiona przed wywołaniem metody onStop


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds"); //ta linijka kodu pobiera zapisany stan wartości Bundle
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        runTimer(); //metoda runTimer zostanie uruchomiona w momencie tworzenia aktywności
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) { //dodając 4 linijki kodu aplikacja podczas zmiany zmuszającej do resetu aktywności zapiszą wartości zmienne
        savedInstanceState.putInt("seconds", seconds);           //
        savedInstanceState.putBoolean("running", running);       //
        savedInstanceState.putBoolean("wasRunning", wasRunning); //
    }

    @Override //wywołana zostaje metoda onStop która zatrzymuje odliczanie stopera
    public void onStop() {
        super.onStop();
        wasRunning = running; //ten kod zapisuje jaki miała status aktywność w momencie onStop
        running = false;
    }

    @Override
    //wywołana metoda onStart która sprawdza czy stoper działał przed implementacją onStop, jeżeli tak to niech działa dalej
    public void onStart() {
        super.onStart();
        if (wasRunning) {
            running = true;
        }
    }

    public void onClickStart(View v) { //metoda wywołana kliknięciem guzika start, zmienia ona wartość boolean na true
        running = true;
    }

    public void onClickStop(View v) { //metoda wywołana poprzez naciśnięcie guzika stop
        running = false;
    }

    public void onClickReset(View v) { //metoda wywołana poprzez naciśnięcie guzika stop, zmienia ona wartość boolean i zeruje utworzoną zmienną
        running = false;
        seconds = 0;
    }

    private void runTimer() {
        final TextView timeView = (TextView) findViewById(R.id.time_view); //utworzenie referencji timeView do textView zlokalizowanego w widoku xml
        final Handler handler = new Handler(); //utworzony zostaje nowy obiekt handler
        handler.post(new Runnable() { //tu wykonana zostaje metoda post z przekazanym do niej obiektem typu Runnable
            @Override
            public void run() {

                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time = String.format("%d:%02d:%02d", hours, minutes, secs);
                timeView.setText(time);

                if (running)

                {
                    seconds++;
                }
                handler.postDelayed(this, 1000); //po umieszczeniu tego kodu wewnątrz metody run będzie wykonywany cyklicznie i zapewnia opóźnienie 1000ms (1s)
            }
        });
    }
}
