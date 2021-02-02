package com.example.kidoslearning;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;

public class ColorsFragment extends Fragment {

    private MediaPlayer mediaPlayer;
    private AudioManager mAudiomanager;
    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangelistener=new AudioManager.OnAudioFocusChangeListener()
    {
        @Override
        public void onAudioFocusChange(int focusChange)
        {
            if(focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK || focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT)
            {
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            }
            else if(focusChange==AudioManager.AUDIOFOCUS_GAIN)
            {
                mediaPlayer.start();
            }
            else if(focusChange==AudioManager.AUDIOFOCUS_LOSS)
            {
                releaseMediaplayer();
            }

        }
    };

    private MediaPlayer.OnCompletionListener mCompletionListener =new MediaPlayer.OnCompletionListener()
    {
        @Override
        public void onCompletion(MediaPlayer mp)
        {
            releaseMediaplayer();
        }
    };

    public ColorsFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootview =inflater.inflate(R.layout.word_list,container,false);
        mAudiomanager=(AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Words> colorlist=new ArrayList<Words>();
        colorlist.add(new Words("Black"," ",R.drawable.color_black,R.raw.black));
        colorlist.add(new Words("Brown"," ",R.drawable.color_brown,R.raw.brown));
        colorlist.add(new Words("Dusty Yellow","",R.drawable.color_dusty_yellow,R.raw.dustyyellow));
        colorlist.add(new Words("Grey","",R.drawable.color_gray,R.raw.grey));
        colorlist.add(new Words("Green","",R.drawable.color_green,R.raw.green));
        colorlist.add(new Words("Mustard Yellow","",R.drawable.color_mustard_yellow,R.raw.mustardyellow));
        colorlist.add(new Words("Red","",R.drawable.color_red,R.raw.red));
        colorlist.add(new Words("White","",R.drawable.color_white,R.raw.white));
        WordsListAdapter coloradapter=new WordsListAdapter(getActivity(),R.layout.listview,colorlist,R.color.ColorColor);
        ListView list=(ListView)rootview.findViewById(R.id.list);
        list.setAdapter(coloradapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Words viewPosition=colorlist.get(position);
                releaseMediaplayer();
                int result=mAudiomanager.requestAudioFocus(mOnAudioFocusChangelistener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result==mAudiomanager.AUDIOFOCUS_REQUEST_GRANTED)
                {
                    mAudiomanager.abandonAudioFocus(mOnAudioFocusChangelistener);
                    mediaPlayer = MediaPlayer.create(getActivity(),viewPosition.getAudio());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });


        return rootview;
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaplayer();
    }

    private void releaseMediaplayer()
    {
        if(mediaPlayer!=null)
        {
            mediaPlayer.release();
            mediaPlayer=null;
            mAudiomanager.abandonAudioFocus(mOnAudioFocusChangelistener);
        }
    }
}