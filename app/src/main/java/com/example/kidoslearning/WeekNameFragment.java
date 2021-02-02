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


public class WeekNameFragment extends Fragment {
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

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaplayer();
        }
    };


  public WeekNameFragment()
  {

  }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootview =inflater.inflate(R.layout.word_list,container,false);
        mAudiomanager=(AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
        ListView listViewweek = rootview.findViewById(R.id.list);
        final ArrayList<Words> weeklist = new ArrayList<Words>();
        weeklist.add(new Words("Monday", "Somvaar", 0, R.raw.monday));
        weeklist.add(new Words("Tuesday", "Mangalvar", 0, R.raw.tuesday));
        weeklist.add(new Words("Wednesday", "Bhudhvaar", 0, R.raw.wednesday));
        weeklist.add(new Words("Thursday", "Guruvar", 0, R.raw.thursday));
        weeklist.add(new Words("Friday", "Shukravaar", 0, R.raw.friday));
        weeklist.add(new Words("Saturday", "Shanivaar", 0, R.raw.saturday));
        weeklist.add(new Words("Sunday", "Ravivaar", 0, R.raw.sunday));
        WordsListAdapter weekAdapter = new WordsListAdapter(getActivity(), R.layout.listview, weeklist, R.color.WeekNameColor);
        listViewweek.setAdapter(weekAdapter);
        listViewweek.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Words viewPosition = weeklist.get(position);
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
        if (mediaPlayer != null)
        {
            mediaPlayer.release();
            mediaPlayer = null;
            mAudiomanager.abandonAudioFocus(mOnAudioFocusChangelistener);

        }
    }
}