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

public class FamilyMembersFragment extends Fragment
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

    public FamilyMembersFragment()
    {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootview =inflater.inflate(R.layout.word_list,container,false);
        mAudiomanager=(AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Words> arrayList=new ArrayList<Words>();
        arrayList.add(new Words("Grand Father","Dadaji",R.drawable.family_grandfather,R.raw.grandfather));
        arrayList.add(new Words("Grand Mother","Dadiji",R.drawable.family_grandmother,R.raw.grandmother));
        arrayList.add(new Words("Father","Papa",R.drawable.family_father,R.raw.father));
        arrayList.add(new Words("Mother","Mummy",R.drawable.family_mother,R.raw.mother));
        arrayList.add(new Words("Younger Sister","Choti Behen",R.drawable.family_younger_sister,R.raw.youngersister));
        arrayList.add(new Words("Elder Sister","Badi Behen",R.drawable.family_older_sister,R.raw.eldersister));
        arrayList.add(new Words("Younger Brother","Chota Bhai",R.drawable.family_younger_brother,R.raw.youngerbrother));
        arrayList.add(new Words("Elder Brother","Bada Bhai",R.drawable.family_older_brother,R.raw.elderbrother));
        arrayList.add(new Words("cousin Sister","Chacher Behen",R.drawable.family_daughter,R.raw.cousinsister));
        arrayList.add(new Words("cousin Brother","Chachera Bhai",R.drawable.family_son,R.raw.cousinbrother));
        WordsListAdapter familyadapter=new WordsListAdapter(getActivity(),R.layout.listview,arrayList,R.color.FamilyMemberColor);
        ListView list=(ListView)rootview.findViewById(R.id.list);
        list.setAdapter(familyadapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Words viewPosition=arrayList.get(position);
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