package com.example.viewmodellivedatademoenrichi;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {

    private CounterViewModal viewModel;

    private TextView tvCount, tvHistory, tvStatus;
    private Button btnIncrement, btnDecrement, btnReset;
    private Button btnStep1, btnStep5, btnStep10, btnBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Liaison des vues
        tvCount       = findViewById(R.id.tvCount);
        tvHistory     = findViewById(R.id.tvHistory);
        tvStatus      = findViewById(R.id.tvStatus);
        btnIncrement  = findViewById(R.id.btnIncrement);
        btnDecrement  = findViewById(R.id.btnDecrement);
        btnReset      = findViewById(R.id.btnReset);
        btnStep1      = findViewById(R.id.btnStep1);
        btnStep5      = findViewById(R.id.btnStep5);
        btnStep10     = findViewById(R.id.btnStep10);
        btnBackground = findViewById(R.id.btnBackground);

        // Recuperation du ViewModel (survit a la rotation)
        viewModel = new ViewModelProvider(this).get(CounterViewModal.class);

        // Observation du compteur principal
        viewModel.getCount().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer newCount) {
                tvCount.setText(String.valueOf(newCount));
            }
        });

        // Observation de l'historique
        viewModel.getHistory().observe(this, history ->
                tvHistory.setText(history));

        // Observation du statut
        viewModel.getStatus().observe(this, status ->
                tvStatus.setText(status));

        // Boutons principaux
        btnIncrement.setOnClickListener(v -> viewModel.increment());
        btnDecrement.setOnClickListener(v -> viewModel.decrement());
        btnReset.setOnClickListener(v -> viewModel.reset());

        // Selecteurs de pas
        btnStep1.setOnClickListener(v  -> viewModel.setStep(1));
        btnStep5.setOnClickListener(v  -> viewModel.setStep(5));
        btnStep10.setOnClickListener(v -> viewModel.setStep(10));

        // Thread background
        btnBackground.setOnClickListener(v -> viewModel.incrementFromBackground());
    }
}