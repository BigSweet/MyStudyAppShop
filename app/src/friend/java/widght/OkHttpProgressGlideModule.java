package widght;

import android.content.Context;
import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.demo.swt.mystudyappshop.Util.glideprogress.ProgressInterceptor;

import java.io.InputStream;

import okhttp3.OkHttpClient;

// TODO add <meta-data android:value="GlideModule" android:name="....OkHttpProgressGlideModule" />
// TODO add <meta-data android:value="GlideModule" tools:node="remove" android:name="com.bumptech.glide.integration.okhttp.OkHttpGlideModule" />
// or not use 'okhttp@aar' in Gradle depdendencies
@GlideModule
public class OkHttpProgressGlideModule extends AppGlideModule {


    public OkHttpProgressGlideModule() {
        super();
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        super.applyOptions(context, builder);
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
//        super.registerComponents(context,glide,registry);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new ProgressInterceptor())
                .build();

        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(okHttpClient));
    }


}