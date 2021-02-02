package com.example.kidoslearning;
public class Words
        {
            public String english_num;
            public String hindi_num;
            public int image;
            public int audio;

            public Words(String english_num, String hindi_num, int image, int audio)
            {
                this.english_num = english_num;
                this.hindi_num = hindi_num;
                this.image = image;
                this.audio = audio;
            }

            public Words(String english_num, String hindi_num, int image)
            {
                this.english_num = english_num;
                this.hindi_num = hindi_num;
                this.image = image;
            }
            public int getAudio()
            {
                return audio;
            }
            public void setAudio(int audio)
            {
                this.audio = audio;
            }
            public String getFirst_half() {
                return english_num;
            }
            public void setFirst_half(String first_half) {
                this.english_num = english_num;
            }
            public String getSecond_half() {
                return hindi_num;
            }
            public void setSecond_half(String second_half) {
                this.hindi_num = hindi_num;
            }
            public int getImage()
            {
                return image;
            }
            public void setImage(int image)
            {
                this.image = image;
            }
            public String getHindi_num()
            {
                return hindi_num;
            }
            public void setHindi_num(String hindi_num)
            {
                this.hindi_num = hindi_num;
            }
            public String getEnglish_num()
            {
                return english_num;
            }
            public void setEnglish_num(String english_num)
            {
                this.english_num = english_num;
            }


        }


