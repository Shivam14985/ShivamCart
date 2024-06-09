package com.example.shivamscart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shivamscart.Adapters.MessageRVAdapter;
import com.example.shivamscart.Models.MessageModal;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;

public class ChatBotActivity extends AppCompatActivity {
    private final String USER_KEY = "user";
    private final String BOT_KEY = "bot";
    private RecyclerView chatsRV;
    private ImageButton sendMsgIB;
    private EditText userMsgEdt;
    private ArrayList<MessageModal> messageModalArrayList;
    private MessageRVAdapter messageRVAdapter;


    private OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);
        chatsRV = findViewById(R.id.idRVChats);
        sendMsgIB = findViewById(R.id.idIBSend);
        userMsgEdt = findViewById(R.id.idEdtMessage);


        messageModalArrayList = new ArrayList<>();

        client = new OkHttpClient();


        sendMsgIB.setOnClickListener(v -> {

            if (userMsgEdt.getText().toString().isEmpty()) {
                Toast.makeText(ChatBotActivity.this, "Please enter your message..", Toast.LENGTH_SHORT).show();
                return;
            }

            sendMessage(userMsgEdt.getText().toString());

            userMsgEdt.setText("");
        });


        messageRVAdapter = new MessageRVAdapter(messageModalArrayList, this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChatBotActivity.this, RecyclerView.VERTICAL, false);


        chatsRV.setLayoutManager(linearLayoutManager);

        chatsRV.setAdapter(messageRVAdapter);
    }

    private void sendMessage(String userMsg) {
        messageModalArrayList.add(new MessageModal(userMsg, USER_KEY));
        messageRVAdapter.notifyDataSetChanged();
        String apiKey = "ndREcUBn22SQPNbR";
        String endpoint = "https://api.openai.com/v1/engines/text-davinci-003/completions";
        String payload = "{\"prompt\": \"" + userMsg + "\", \"temperature\": 0.7, \"max_tokens\": 3000}";
        String auth = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            auth = Base64.getEncoder().encodeToString(("openai:" + apiKey).getBytes(StandardCharsets.UTF_8));
        }

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), payload);
        Request request = new Request.Builder()
                .url(endpoint)
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic " + auth)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                messageModalArrayList.add(new MessageModal("Sorry no response found", BOT_KEY));
                runOnUiThread(() -> {
                    Toast.makeText(ChatBotActivity.this, "No response from the bot..", Toast.LENGTH_SHORT).show();
                    messageRVAdapter.notifyDataSetChanged();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    if (responseBody != null) {
                        String jsonResponse = responseBody.string();
                        JsonObject jsonObject = new Gson().fromJson(jsonResponse, JsonObject.class);
                        JsonArray choicesArray = jsonObject.getAsJsonArray("choices");
                        JsonObject choiceObject = choicesArray.get(0).getAsJsonObject();
                        String generatedText = choiceObject.get("text").getAsString();
                        messageModalArrayList.add(new MessageModal(generatedText, BOT_KEY));
                        runOnUiThread(() -> messageRVAdapter.notifyDataSetChanged());
                    }
                } else {
                    messageModalArrayList.add(new MessageModal("No response", BOT_KEY));
                    runOnUiThread(() -> messageRVAdapter.notifyDataSetChanged());
                }
            }
        });
    }
}