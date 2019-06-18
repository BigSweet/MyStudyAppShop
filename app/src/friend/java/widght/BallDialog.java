package widght;

import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.swt.mystudyappshop.R;


/**
 * Created by ccy on 2017-08-09.
 */

public class BallDialog extends DialogFragment {

    BounceBallView bbv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.ball_dialog);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bounce_ball_dialog, container);
        bbv = (BounceBallView) v.findViewById(R.id.ball_view);
        bbv.start();
        return v;
    }

    public BounceBallView getBBV() {
        return bbv;
    }


    @Override
    public int show(FragmentTransaction transaction, String tag) {
        return super.show(transaction, tag);
    }
}
