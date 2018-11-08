package com.example.floating;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * 时间：2018/8/27 15:59
 * 姓名：韩晓康
 * 功能：
 */
public class BasEvaluator implements TypeEvaluator<PointF> {
    private PointF p1;
    private PointF p2;

    public BasEvaluator(PointF p1, PointF p2) {
        super();
        this.p1 = p1;
        this.p2 = p2;
    }

    /**
     * @param v  进度
     * @param p0  起始点
     * @param p3  结束点
     */
    @Override
    public PointF evaluate(float v, PointF p0, PointF p3) {
        PointF pointf=new PointF();
        float t=1-v;
        pointf.x=p0.x*t*t*t+3*p1.x*v*t*t+3*p2.x*v*v*t+p3.x*v*v*v;
        pointf.y=p0.y*t*t*t+3*p1.y*v*t*t+3*p2.y*v*v*t+p3.x*v*v*v;
        return pointf;
    }
}
