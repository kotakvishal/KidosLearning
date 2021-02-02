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

public class AlphabetFragment extends Fragment
{

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

    public AlphabetFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootview =inflater.inflate(R.layout.word_list,container,false);
        mAudiomanager=(AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
        ListView list=rootview.findViewById(R.id.list);
        final ArrayList<Words> alphalist=new ArrayList<Words>();
        alphalist.add(new Words("A","for Apple",0,R.raw.a));
        alphalist.add(new Words("B","for Ball",0,R.raw.b));
        alphalist.add(new Words("C","for Cat",0,R.raw.c));
        alphalist.add(new Words("D","for Dog",0,R.raw.d));
        alphalist.add(new Words("E","for Elephant",0,R.raw.e));
        alphalist.add(new Words("F","for Fan",0,R.raw.f));
        alphalist.add(new Words("G","for Gun",0,R.raw.g));
        alphalist.add(new Words("H","for Horse",0,R.raw.h));
        alphalist.add(new Words("I","for Ice Cream",0,R.raw.i));
        alphalist.add(new Words("J","for Jug",0,R.raw.j));
        alphalist.add(new Words("K","for Kite",0,R.raw.k));
        alphalist.add(new Words("L","for Lion",0,R.raw.l));
        alphalist.add(new Words("M","for mango",0,R.raw.m));
        alphalist.add(new Words("N","for Nest",0,R.raw.n));
        alphalist.add(new Words("O","for Orange",0,R.raw.o));
        alphalist.add(new Words("P","for Parrot",0,R.raw.p));
        alphalist.add(new Words("Q","for Queen",0,R.raw.q));
        alphalist.add(new Words("R","for Rat",0,R.raw.r));
        alphalist.add(new Words("S","for Sparrow",0,R.raw.s));
        alphalist.add(new Words("T","for Tomato",0,R.raw.t));
        alphalist.add(new Words("U","for Umbrella",0,R.raw.u));
        alphalist.add(new Words("V","for Van",0,R.raw.v));
        alphalist.add(new Words("W","for Watch",0,R.raw.w));
        alphalist.add(new Words("X","for X-mas",0,R.raw.x));
        alphalist.add(new Words("Y","for Yawn",0,R.raw.y));
        alphalist.add(new Words("Z","for Zebra",0,R.raw.z));
        WordsListAdapter alphaadapter=new WordsListAdapter (getActivity(),R.layout.listview,alphalist,R.color.AlphabetColor);
        list.setAdapter(alphaadapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Words viewposition=alphalist.get(position);
                releaseMediaplayer();
                int result=mAudiomanager.requestAudioFocus(mOnAudioFocusChangelistener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result==mAudiomanager.AUDIOFOCUS_REQUEST_GRANTED)
                {
                    mAudiomanager.abandonAudioFocus(mOnAudioFocusChangelistener);
                    mediaPlayer = MediaPlayer.create(getActivity(),viewposition.getAudio());
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