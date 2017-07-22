package artts.android.karrar.arabictts.utls;

import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

/**
 * Created by karrar on 7/21/17.
 */

public class ArabicTTS {

    // Used to conver to basic latin form
    private static String[] arabic = {"ء","أ","ؤ","ا","ئ","آ","ى","ب","ت","ث","ج","ح","خ","د","ذ","ر","ز","س","ش","ص","ض","ط","ظ","ع","غ","ف","ق","ك","ل","م","ن","ه","ة","و","ي","پ‎","چ","ڜ","ڥ","ڤ","ݣ","گ","ڨ","؟",",","!","۰","۱","٢","٣","٤","٥","٦","٧","٨","٩"};
    private static String[] english = {"a","a","a","a","a","a","a","b","t","th","dj","h","kh","d","dh","r","z","s","sh","s","d","t","dh","a","gh","f","q","k","l","m","n","h","a","uo","y","p","ch","tch","v","v","g","g","g","?",",","!","0","1","2","3","4","5","6","7","8","9"};
    // Used to fix some mistakes
    private static String[] mistakes = {"bay","dai","sad","bar","kah","aaa","kau","oan","tuo","yam","gar"," uo","saf","maz","maw","yaw","wab","kas","mach","wak","has","zam","aya","mar","tan","sar","way "," man ","hawak","rad","i ","bay","law","way","lalah"," maw "," maw?","maw ","yar","tak","zab","nay","aay"," aa","nai"};
    private static String[] fixes = {"bi","di","sed","ber","kh","aa","ku","on","tou","eym","gur"," wa","sif","muz","moo","eoo","ob","kos","mich","ok","hass","zom","aia","mer","taan","sur","oee "," min ","hook","red","y ","bi","loo","we","llah"," mo "," mo?","mo ","yer","tik","zeb","ni","ai"," a","ni"};

    private static String[] arnumbers = {"0","١","1","2","3","4","5","6","7","8","9"};
    private static String[] ennumbers = {" suffrr "," waheed "," waheed "," ethaneen "," sallassa "," arurbaa "," khamssa "," setta "," sabaa "," sammania "," tessaa "};



    // The text to speech we will use
    private TextToSpeech tts;

    // So that it won't re convert a converted line
    private String latest = "";

    // To create the text to speech
    public boolean prepare(Context con){
        if(con != null)
        {

            tts = new TextToSpeech(con, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status != TextToSpeech.ERROR) {
                        Locale loc = new Locale("en", "IN");
                        tts.setLanguage(loc);
                    }
                }
            });
            return true;
        }
        else
        {
            return false;
        }
    }


    // To convert text to speech
    public boolean talk(String text){
        if(!text.equals(latest)) {
            text = filter(text);
        }
        if(tts!=null && text != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
            } else {
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
            }
            return true;
        }else{
            return false;
        }
    }


    // Filtering the text into latin
    public String filter(String text) {
        while(text.contains("  ")){
            text = text.replace("  "," ");
        }
        text = " "+text+" ";
        // convert to basic latin
        text = convert(text,1);
        // fix mistakes
        text = convert(text,2);
        // convert numbers
        text = convert(text,3);
        latest = text;
        return text;
    }



    // Converting into latin
    private String convert(String text,int type){

        String[] fromlist = null;
        String[] tolist = null;

        if(type == 1){
            fromlist = arabic;
            tolist = english;
        }else if(type == 2){
            fromlist = mistakes;
            tolist = fixes;
        }else if(type == 3){
            fromlist = arnumbers;
            tolist = ennumbers;
        }

        for(int x = 0;x<fromlist.length;x++){
            if(text.contains(fromlist[x])){
                if(type == 1) {
                    text = text.replace(fromlist[x], tolist[x] + "a");
                }else{
                    text = text.replace(fromlist[x], tolist[x]);
                }
            }
        }
        if(type == 1)
        text = text.replace("a "," ");
        return text;
    }


}
