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

public class NumbersFragment extends Fragment
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


    public NumbersFragment()
    {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootview =inflater.inflate(R.layout.word_list,container,false);
        mAudiomanager=(AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        Words one =new Words(" one"," ek",R.drawable.number_one,R.raw.one);
        Words two =new Words(" two"," do",R.drawable.number_two,R.raw.two);
        Words three =new Words(" three"," teen",R.drawable.number_three,R.raw.three);
        Words four =new Words(" four"," char",R.drawable.number_four,R.raw.four);
        Words five =new Words(" five"," paanch",R.drawable.number_five,R.raw.five);
        Words six =new Words(" six"," che",R.drawable.number_six,R.raw.six);
        Words seven =new Words(" seven"," saat",R.drawable.number_seven,R.raw.seven);
        Words eight =new Words(" eight"," aanth",R.drawable.number_eight,R.raw.eight);
        Words nine =new Words(" nine"," nau",R.drawable.number_nine,R.raw.nine);
        Words ten =new Words(" ten"," das",R.drawable.number_ten,R.raw.ten);
        final ArrayList<Words> arrayList=new ArrayList<Words>();
        arrayList.add(one);
        arrayList.add(two);
        arrayList.add(three);
        arrayList.add(four);
        arrayList.add(five);
        arrayList.add(six);
        arrayList.add(seven);
        arrayList.add(eight);
        arrayList.add(nine);
        arrayList.add(ten);
        ListView myList = rootview.findViewById(R.id.list);
        WordsListAdapter adapter=new WordsListAdapter(getActivity(),R.layout.listview,arrayList,R.color.NumberColor);
        myList.setAdapter(adapter);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseMediaplayer();
                Words viewPosition=arrayList.get(position);
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