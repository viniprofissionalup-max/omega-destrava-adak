package com.omega.adakunlock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScrollView scroll = new ScrollView(this);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        int pad = dp(20);
        root.setPadding(pad, pad, pad, pad);

        TextView title = new TextView(this);
        title.setText("Omega - Destravar Adak");
        title.setTextSize(22);
        title.setPadding(0, 0, 0, dp(16));
        root.addView(title);

        TextView hint = new TextView(this);
        hint.setText("Toque num botao abaixo. Se abrir a tela SOBRE, toque 7 vezes no numero da versao para ligar o modo desenvolvedor.");
        hint.setPadding(0, 0, 0, dp(20));
        root.addView(hint);

        root.addView(makeButton("1) Abrir tela SOBRE (toque 7x aqui)",
                Settings.ACTION_DEVICE_INFO_SETTINGS));
        root.addView(makeButton("2) Abrir OPCOES DO DESENVOLVEDOR",
                Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS));
        root.addView(makeButton("3) Abrir CONFIGURACOES gerais",
                Settings.ACTION_SETTINGS));

        Button pkgBtn = new Button(this);
        pkgBtn.setText("4) Forcar abrir app de Configuracoes");
        pkgBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Intent i = getPackageManager()
                            .getLaunchIntentForPackage("com.android.settings");
                    if (i != null) {
                        startActivity(i);
                    } else {
                        toast("Nao achei o app de Configuracoes (com.android.settings).");
                    }
                } catch (Exception e) {
                    toast("Erro: " + e.getMessage());
                }
            }
        });
        root.addView(pkgBtn);

        scroll.addView(root);
        setContentView(scroll);
    }

    private Button makeButton(String text, final String action) {
        Button b = new Button(this);
        b.setText(text);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Intent i = new Intent(action);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                } catch (Exception e) {
                    toast("Nao deu por aqui: " + e.getMessage());
                }
            }
        });
        return b;
    }

    private void toast(String m) {
        Toast.makeText(this, m, Toast.LENGTH_LONG).show();
    }

    private int dp(int v) {
        return (int) (v * getResources().getDisplayMetrics().density);
    }
}
