package com.example.floating;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;

/**
 * 时间：2018/8/27 13:38
 * 姓名：韩晓康
 * 功能：
 */
public class FloatingIv extends RelativeLayout{

    private Context context;
    private int widthSpec;
    private int heightSpec;
    private int[] img=new int[]{R.mipmap.heart,R.mipmap.heart1,R.mipmap.heart2,R.mipmap.heart3};
    private LayoutParams params;

    private Interpolator[] interpolators = new Interpolator[4];
    private Bitmap bitmap;
    private BitmapFactory.Options options;

    public FloatingIv(Context context) {
        this(context,null);
    }

    public FloatingIv(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public FloatingIv(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        init();
    }

    public void init() {
        options = new BitmapFactory.Options();//处理图片尺寸获取不符情况
        options.inScaled=false;
        bitmap = BitmapFactory.decodeResource(context.getResources(), img[0],options);

        interpolators[0] = new AccelerateDecelerateInterpolator(); // 在动画开始与结束的地方速率改变比较慢，在中间的时候加速
        interpolators[1] = new AccelerateInterpolator();  // 在动画开始的地方速率改变比较慢，然后开始加速
        interpolators[2] = new DecelerateInterpolator(); // 在动画开始的地方快然后慢
        interpolators[3] = new LinearInterpolator();  // 以常量速率改变

        params = new LayoutParams(bitmap.getWidth(), bitmap.getWidth());
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        addImv();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        widthSpec = widthSpec(widthMeasureSpec);
        heightSpec = heightSpec(heightMeasureSpec);
        setMeasuredDimension(widthSpec, heightSpec);
    }

    /**
     * 作者  韩晓康
     * 时间  2018/8/28 10:06
     * 描述  添加 图片
     */
    private ImageView addImv() {
        bitmap = BitmapFactory.decodeResource(context.getResources(), img[new Random().nextInt(img.length)],options);
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(params);
        imageView.setImageBitmap(bitmap);
        addView(imageView);
        start(imageView);
        return imageView;
    }

    /**
     * 作者  韩晓康
     * 时间  2018/8/28 10:06
     * 描述  点击监听
     */
    public void start(final ImageView imageView) {
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    startAnimator(imageView);

                }
            });
    }

    /**
     * 作者  韩晓康
     * 时间  2018/8/28 10:06
     * 描述  开始贝塞尔曲线动画
     */
    private void startAnimator(final ImageView imageView) {
        addImv();
        ValueAnimator bzier = getBzierAnimator(imageView);
        bzier.start();
        bzier.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                removeView(imageView);//这里是循环添加View 在动画结束之后需要移除
            }
        });
    }

    /**
     * 作者  韩晓康
     * 时间  2018/8/28 10:06
     * 描述  贝塞尔曲线动画
     */
    private ValueAnimator getBzierAnimator(final ImageView iv) {
        PointF[] PointFs = getPointFs(); // 4个点的坐标
        BasEvaluator evaluator = new BasEvaluator(PointFs[1], PointFs[2]);
        ValueAnimator valueAnim = ValueAnimator.ofObject(evaluator, PointFs[0], PointFs[3]);
        valueAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF p = (PointF) animation.getAnimatedValue();//当前动画的值
                iv.setX(p.x);
                iv.setY(p.y);
                iv.setAlpha(1- animation.getAnimatedFraction()); // 透明度
            }
        });
        valueAnim.setTarget(iv);
        valueAnim.setDuration(2500);
        valueAnim.setInterpolator(interpolators[new Random().nextInt(4)]);
        return valueAnim;
    }
    /**
     * 作者  韩晓康
     * 时间  2018/8/28 10:06
     * 描述  获取坐标点
     */
    private PointF[] getPointFs() {
        PointF[] PointFs = new PointF[4];
        PointFs[0] = new PointF(); // 开始点  坐标
        PointFs[0].x = (widthSpec- bitmap.getWidth())/ 2;
        PointFs[0].y = heightSpec - bitmap.getHeight()-20;

        PointFs[1] = new PointF(); // p1
        PointFs[1].x = new Random().nextInt(widthSpec-5);
        PointFs[1].y = new Random().nextInt(heightSpec /2) + heightSpec / 2 ;

        PointFs[2] = new PointF(); // p2
        PointFs[2].x = new Random().nextInt(widthSpec-5);
        PointFs[2].y = new Random().nextInt(heightSpec /2);

        PointFs[3] = new PointF(); // 结束点坐标
        PointFs[3].x = new Random().nextInt(widthSpec-5);
        PointFs[3].y = 0;
        return PointFs;
    }

    private int widthSpec(int widthMeasureSpec) {
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int ressult;
        if (mode== MeasureSpec.EXACTLY){
            ressult=size;
        }else {
            ressult=getWidth();
            if (mode== MeasureSpec.AT_MOST){
                ressult=Math.min(ressult,size);
            }
        }
        return ressult;
    }
    private int heightSpec(int heightMeasureSpec) {
        int size = MeasureSpec.getSize(heightMeasureSpec);
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int ressult;
        if (mode== MeasureSpec.EXACTLY){
            ressult=size;
        }else {
            ressult=getWidth();
            if (mode== MeasureSpec.AT_MOST){
                ressult=Math.min(ressult,size);
            }
        }
        return ressult;
    }


    public void setImg(int[] img) {
        this.img = img;
        addImv();
    }

    /**
     * 作者  韩晓康
     * 时间  2018/8/28 10:06
     * 描述  开外部调用 点击开始
     */
    public void clickStart() {
        ImageView imageView = addImv();
        startAnimator(imageView);
    }
}
