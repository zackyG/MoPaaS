package com.mopaas_mobile.utils;

import android.widget.RelativeLayout;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Display;

//
// InputMethodRelativeLayout.java by Administrator at 下午5:22:27
//
// Copyright (c) 2012 anchora,inc
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// THE SOFTWARE.

/**
 * 
 * @author junjun
 * 自定义布局解决键盘弹出挡住输入框的问题
 */
public class InputMethodRelativeLayout extends RelativeLayout {
	private int width;
	protected OnSizeChangedListenner onSizeChangedListenner;
	private boolean sizeChanged  = false; //变化的标志
	private int height;
	private int screenWidth; //屏幕宽度
	private int screenHeight; //屏幕高度

	public InputMethodRelativeLayout(Context paramContext,
			AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		Display localDisplay = ((Activity) paramContext).getWindowManager()
				.getDefaultDisplay();
		this.screenWidth = localDisplay.getWidth() ;
		this.screenHeight = localDisplay.getHeight();
	}
	public InputMethodRelativeLayout(Context paramContext,
			AttributeSet paramAttributeSet, int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		this.width = widthMeasureSpec;
		this.height = heightMeasureSpec;
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	@Override
	public void onSizeChanged(int w, int h, int oldw,
			int oldh) {
		//监听不为空、宽度不变、当前高度与历史高度不为0 
		if ((this.onSizeChangedListenner!= null) && (w == oldw) && (oldw != 0)
				&& (oldh != 0)) {
			if ((h >= oldh)
					|| (Math.abs(h - oldh) <= 1 * this.screenHeight / 4)) {
				if ((h <= oldh)
						|| (Math.abs(h - oldh) <= 1 * this.screenHeight / 4))
					return;
				this.sizeChanged  = false;
			} else {
				this.sizeChanged  = true;
			}
			this.onSizeChangedListenner.onSizeChange(this.sizeChanged ,oldh, h);
			measure(this.width - w + getWidth(), this.height
					- h + getHeight());
		}
	}
	/**
	 * 设置视图偏移的监听事件
	 * @param paramonSizeChangedListenner
	 */
	public void setOnSizeChangedListenner(
			InputMethodRelativeLayout.OnSizeChangedListenner paramonSizeChangedListenner) {
		this.onSizeChangedListenner = paramonSizeChangedListenner;
	}
	/**
	 * 视图偏移的内部接口
	 * @author junjun
	 *
	 */
	public abstract interface OnSizeChangedListenner {
		public abstract void onSizeChange(boolean paramBoolean, int w,int h);
	}
}

