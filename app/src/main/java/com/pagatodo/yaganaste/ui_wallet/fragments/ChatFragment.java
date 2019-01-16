package com.pagatodo.yaganaste.ui_wallet.fragments;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui_wallet.chat.ChatTextAdapater;
import com.pagatodo.yaganaste.ui_wallet.holders.ChatBoxGrayTextViewHolder;
import com.pagatodo.yaganaste.ui_wallet.holders.ChatBoxTextViewHolder;
import com.pagatodo.yaganaste.utils.customviews.StyleEdittext;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import ai.api.AIDataService;
import ai.api.AIListener;
import ai.api.AIServiceException;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Status;
import butterknife.BindView;
import butterknife.ButterKnife;

import static androidx.annotation.Dimension.SP;

public class ChatFragment extends GenericFragment implements AIListener, View.OnClickListener{

    private LayoutInflater inflater;
    private View rootView;
    //private ViewGroup parent;
    private AIService aiService;
    private ChatTextAdapater adapater;

    @BindView(R.id.editText)
    StyleEdittext editText;
    @BindView(R.id.row_send)
    ImageView rowSend;
    @BindView(R.id.container_chat)
    ListView containerChat;

    final AIConfiguration config = new AIConfiguration("bc492796f0854f3d91635b1546fa0625",
            AIConfiguration.SupportedLanguages.Spanish,
            AIConfiguration.RecognitionEngine.System);
    final AIDataService aiDataService = new AIDataService(config);
    final AIRequest aiRequest = new AIRequest();

    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflater = LayoutInflater.from(getContext());
        adapater = new ChatTextAdapater(getContext());
        aiService = AIService.getService(getContext(), config);
        aiService.setListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.chat_layout, container, false);
        initViews();
        return rootView;
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        containerChat.setAdapter(adapater);
        rowSend.setOnClickListener(this);
        //containerChat.addView(setTextDate());
        //containerChat.addView(setViewLine());
        adapater.addChatText(new ChatBoxTextViewHolder.Chatholder("Hola ¿Cómo podemos ayudarte?",getTime(),true));
        //setChatTextRobot(containerChat,new ChatBoxTextViewHolder.Chatholder("Hola ¿Cómo podemos ayudarte?",getTime())).inflate(containerChat);

    }

    @Override
    public void onResult(AIResponse result) {
        final Status status = result.getStatus();
        Log.i("CHAT", "Status code: " + status.getCode());
        Log.i("CHAT", "Status type: " + status.getErrorType());
    }

    @Override
    public void onError(AIError error) {

    }

    @Override
    public void onAudioLevel(float level) {

    }

    @Override
    public void onListeningStarted() {

    }

    @Override
    public void onListeningCanceled() {

    }

    @Override
    public void onListeningFinished() {

    }

    public ChatBoxTextViewHolder setChatTextUser(ViewGroup parent,
                                               ChatBoxTextViewHolder.Chatholder item){
        View layout = inflater.inflate(R.layout.chat_box_layout, parent, false);
        ChatBoxTextViewHolder holder = new ChatBoxTextViewHolder(layout);
        holder.bind(item,null);
        return holder;
    }
    public ChatBoxGrayTextViewHolder setChatTextRobot(ViewGroup parent,
                                                     ChatBoxTextViewHolder.Chatholder item){
        View layout = inflater.inflate(R.layout.chat_box_gray_layout, parent, false);
        ChatBoxGrayTextViewHolder holder = new ChatBoxGrayTextViewHolder(layout);
        holder.bind(item,null);
        return holder;
    }

    public StyleTextView setTextDate(){
        StyleTextView textData = new StyleTextView(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        textData.setLayoutParams(params);
        textData.setTextColor(getResources().getColor(R.color.texthint));
        textData.setTextSize(SP,10);
        textData.setGravity(Gravity.CENTER_HORIZONTAL);
        textData.setText(getTime());
        return textData;
    }

    public View setViewLine(){
        View view = new View(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                2);
        params.setMargins(0, 6, 0, 8);
        view.setLayoutParams(params);
        view.setBackgroundResource(R.color.line);
        return view;
    }

    @Override
    public void onClick(View view) {

        adapater.addChatText(new ChatBoxTextViewHolder.Chatholder(editText.getText().toString().trim(), getTime(),false));
        //setChatTextUser(containerChat,new ChatBoxTextViewHolder.Chatholder(editText.getText().toString().trim(), getTime())).inflate(containerChat);
        aiRequest.setQuery(editText.getText().toString().trim());
        editText.setText("");
        getConversation().execute(aiRequest);
    }

    public String getTime(){
        @SuppressLint("SimpleDateFormat") DateFormat formatter = new SimpleDateFormat("HH:mm");
        formatter.setTimeZone(TimeZone.getTimeZone("Mexico/General"));
        return formatter.format(new Date()) + " hrs";
    }

    @SuppressLint("StaticFieldLeak")
    public AsyncTask<AIRequest, Void, AIResponse> getConversation() {
       return new AsyncTask<AIRequest, Void, AIResponse>() {
            @Override
            protected AIResponse doInBackground(AIRequest... requests) {
                final AIRequest request = requests[0];
                try {
                    return aiDataService.request(aiRequest);
                } catch (AIServiceException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(AIResponse aiResponse) {
                if (aiResponse != null) {
                    // process aiResponse here
                    Log.i("CHAT", "Status code: " + aiResponse.getResult().getAction());
                    Log.i("CHAT", "Resolved query: " + aiResponse.getResult().getResolvedQuery());
                    Log.i("CHAT", "Speech: " + aiResponse.getResult().getFulfillment().getSpeech());
                    adapater.addChatText(new ChatBoxTextViewHolder.Chatholder(aiResponse.getResult().getFulfillment().getSpeech(),getTime(),true));
                    //setChatTextRobot(containerChat,
                      //      new ChatBoxTextViewHolder.Chatholder(aiResponse.getResult().getFulfillment().getSpeech(),getTime())).inflate(containerChat);
                }
            }
        };
    }
}
