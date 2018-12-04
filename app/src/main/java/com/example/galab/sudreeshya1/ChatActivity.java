package com.example.galab.sudreeshya1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.engineio.client.Socket;
import com.github.nkzawa.socketio.client.IO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private EditText messageTxt;
    private TextView messageField;
    private Button sendBtn;
    public boolean like = true;
    public com.github.nkzawa.socketio.client.Socket socket;

    private LinearLayoutManager linearLayoutManager;
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    public static ArrayList<dataModelChat> ModelChat;

    private DividerItemDecoration dividerItemDecoration;
    static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;

    public Emitter.Listener onNewMessage = new Emitter.Listener() {

        @Override
        public void call(final Object... args) {

            System.out.println("Message is ");

            ChatActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String message;
                    try{
                        message = data.getString("message");
                        System.out.println("Message is "+message);
                        dataModelChat dataModel = new dataModelChat();
                        dataModel.setMessage(message);
                        ModelChat.add(dataModel);
                        messageField.setText(message);
                        recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount()-1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

  
    private com.github.nkzawa.socketio.client.Socket msocket;
    {

        try{
            msocket = IO.socket("https://galab-chat.herokuapp.com/");
            msocket.emit("msg", "User connected");
        } catch (URISyntaxException e) {
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recyclerView = findViewById(R.id.recyclerView);

        recyclerView = findViewById(R.id.recyclerView);
        ModelChat = new ArrayList<>();
        adapter = new MessageAdapter(getApplicationContext(), ModelChat);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);

        msocket.on("message",onNewMessage);
        msocket.connect();






        sendBtn = findViewById(R.id.sendBtn);
        messageField = findViewById(R.id.messageField);

        messageTxt = findViewById(R.id.messageTxt);
        messageTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(messageTxt.getText().toString().trim())){
                    sendBtn.setBackgroundResource(R.drawable.ic_send_black_24dp);
                    like = false;
                }
                else {
                    sendBtn.setBackgroundResource(R.drawable.ic_thumb_up_black_24dp);
                    like = true;
                }
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!like){
                    msocket.emit("msg", messageTxt.getText().toString().trim());


                    messageTxt.setText("");
                }

            }
        });

//        try {
//            socket = IO.socket("http://192.168.1.87:5000");
//            socket.connect();
//            String message = "galab";
//
//            socket.emit("msg", message);
//
//
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }




    }

    private void attemptSend() {


    }

}
