package fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.demo.swt.mystudyappshop.R;
import com.demo.swt.mystudyappshop.Util.GifSizeFilter;
import com.demo.swt.mystudyappshop.Util.Glide4Engine;
import com.demo.swt.mystudyappshop.Wight.SwtToast;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zhihu.matisse.listener.OnCheckedListener;
import com.zhihu.matisse.listener.OnSelectedListener;

import java.util.HashMap;
import java.util.List;

import activity.AnimationActivity;
import activity.BaiDuActivity;
import activity.BounceBallActivity;
import activity.CameraDemoActivity;
import activity.ChatJiqiActivity;
import activity.CstKeyBoardActivity;
import activity.CstScrollVIewctivity;
import activity.CstTouchViewActivity;
import activity.CstWangZheGameActivity;
import activity.DownloadAppActivity;
import activity.FlowLayoutActivity;
import activity.GreenMainAcitivity;
import activity.LeiDaActivity;
import activity.LoadMoreCombinationFcActivity;
import activity.MDFixScrollActivity;
import activity.MarqeeViewActivity;
import activity.NesScrollViewActivty;
import activity.NineLockActivity;
import activity.PcToAndroidActivity;
import activity.PlayViewActivity;
import activity.PopWindowsActivity;
import activity.QrCodeActivity;
import activity.RecordDemoActivity;
import activity.RgbaActivity;
import activity.RulerViewActivity;
import activity.SampleActivity;
import activity.SelectCityActivity;
import activity.SqliteDataBaseActivity;
import activity.SwipeMatchActivity;
import activity.TanTanActivity;
import activity.TextViewAndImageViewActivity;
import activity.TranActivity;
import activity.VideoWapperActivity;
import activity.VoteActivity;
import activity.WebChatActivity;
import activity.WebViewActivity;
import activity.WuZiQiActivity;
import activity.ZhiMaLeiDaActivity;
import activity.ZhiWenActivity;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import widght.TouchHelpActivity;

/**
 * Created by pc on 2016/11/29.
 */

public class TabFenLeiFragment extends Fragment {


    public static TabFenLeiFragment newInstance() {
        Bundle args = new Bundle();
        TabFenLeiFragment fragment = new TabFenLeiFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category, container, false);

        view.findViewById(R.id.select_city).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SelectCityActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.zhima).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ZhiMaLeiDaActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.rgbabutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RgbaActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.qrcodebutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), QrCodeActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.webchatbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WebChatActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.recorddemobutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RecordDemoActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.ninebutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NineLockActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.smsservice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开注册页面
                RegisterPage registerPage = new RegisterPage();
                registerPage.setRegisterCallback(new EventHandler() {
                    public void afterEvent(int event, int result, Object data) {
                        // 解析注册结果
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            @SuppressWarnings("unchecked")
                            HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                            String country = (String) phoneMap.get("country");
                            String phone = (String) phoneMap.get("phone");
                            SwtToast.show("地区" + country + "手机号" + phone + "的用户验证成功");
                        }
                    }
                });
                registerPage.show(getActivity());
            }
        });
        view.findViewById(R.id.aqlitebutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SqliteDataBaseActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.chatjiqiren).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChatJiqiActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.wuziqi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WuZiQiActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.butt_ditu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BaiDuActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.donghua).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AnimationActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.camrea).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CameraDemoActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.flow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FlowLayoutActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.green).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GreenMainAcitivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.leidasoamiao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LeiDaActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.bendixiangce).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getPhoto();
                Intent intent = new Intent(getActivity(), SampleActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.kapian).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TanTanActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.playview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PlayViewActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.update_app).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DownloadAppActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.tuwen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TextViewAndImageViewActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.toumingzhuomian).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TranActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.shipinzhuomian).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), VideoWapperActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.swipe_match).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SwipeMatchActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.ruler_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RulerViewActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.cst_scroll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CstScrollVIewctivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.cst_touch_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CstTouchViewActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.wangzherongyao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CstWangZheGameActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.pc_to_anroid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PcToAndroidActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.touch_news).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TouchHelpActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.key_board).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CstKeyBoardActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.nes_scroll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NesScrollViewActivty.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.md_nes_scroll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MDFixScrollActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.marqee_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MarqeeViewActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.web_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.ball).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BounceBallActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.zhiwen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ZhiWenActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.loadmore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoadMoreCombinationFcActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.vote).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), VoteActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.popwindoes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PopWindowsActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    /*    List<Uri> mSelected;
        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == REQUEST_CODE_CHOOSE && resultCode == 0) {
    //            mSelected = Matisse.obtainResult(data);
                Log.d("Matisse", "mSelected: " + mSelected);
            }
        }*/


    public void getPhoto() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Toast.makeText(getActivity(), "request read external storage", Toast.LENGTH_LONG).show();
                }
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
            } else {
                Log.d("swt", "权限成功");
                requestPhotoSuccess();
            }
        } else {
            Log.d("swt", "少于6.0");
        }


    }

    private static final int REQUEST_CODE_CHOOSE = 1;

    public void requestPhotoSuccess() {
        Matisse.from(getActivity())
                .choose(MimeType.ofAll(), false)
                .countable(true)
                .capture(true)
                .captureStrategy(
                        new CaptureStrategy(true, "com.zhihu.matisse.sample.fileprovider"))
                .maxSelectable(9)
                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(
                        getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                // for glide-V3
//                                            .imageEngine(new GlideEngine())
                // for glide-V4
                .imageEngine(new Glide4Engine())
                .setOnSelectedListener(new OnSelectedListener() {
                    @Override
                    public void onSelected(
                            @NonNull List<Uri> uriList, @NonNull List<String> pathList) {
                        // DO SOMETHING IMMEDIATELY HERE
                        Log.e("onSelected", "onSelected: pathList=" + pathList);

                    }
                })
                .originalEnable(true)
                .maxOriginalSize(10)
                .setOnCheckedListener(new OnCheckedListener() {
                    @Override
                    public void onCheck(boolean isChecked) {
                        // DO SOMETHING IMMEDIATELY HERE
                        Log.e("isChecked", "onCheck: isChecked=" + isChecked);
                    }
                })
                .forResult(REQUEST_CODE_CHOOSE);

    /*    Matisse.from(getActivity())
                .choose(MimeType.ofImage())
                .theme(R.style.Matisse_Dracula)
                .countable(false)
                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .maxSelectable(9)
                .originalEnable(true)
                .maxOriginalSize(10)
                .imageEngine(new PicassoEngine())
                .forResult(REQUEST_CODE_CHOOSE);*/
    }


}
