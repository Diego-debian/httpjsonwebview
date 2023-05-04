package unal.todosalau.httpjsonwebview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private WebView webView; // Vista WebView para mostrar los datos
    private Button btnObtenerDatos; // Botón para obtener los datos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtener las vistas
        webView = findViewById(R.id.webview);
        btnObtenerDatos = findViewById(R.id.btn_obtener_datos);

        // Asignar un listener al botón para hacer la solicitud HTTP
        btnObtenerDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Hacer la solicitud HTTP
                String url = "https://jsonplaceholder.typicode.com/todos/1";
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Procesar la respuesta JSON
                                Gson gson = new Gson();
                                String html = "<html><body><h1>Datos obtenidos</h1><table>";
                                try {
                                    // Iterar sobre las claves y valores del objeto JSON
                                    Iterator<String> keys = response.keys();
                                    while (keys.hasNext()) {
                                        String key = keys.next();
                                        String value = response.getString(key);
                                        html += "<tr><td>" + key + "</td><td>" + value + "</td></tr>";
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                html += "</table></body></html>";

                                // Cargar los datos en el WebView
                                webView.loadData(html, "text/html", "UTF-8");
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Manejar errores de solicitud
                                Toast.makeText(MainActivity.this, "Error al obtener los datos", Toast.LENGTH_SHORT).show();
                            }
                        });
                // Agregar la solicitud a la cola de solicitudes de Volley
                Volley.newRequestQueue(MainActivity.this).add(jsonObjectRequest);
            }
        });
    }
}
