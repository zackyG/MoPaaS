package com.mopaas_mobile.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

//
// BorderTextView.java by Administrator at 上午11:04:27
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
public class BorderTextView extends TextView {

		public BorderTextView(Context context) {
			super(context);
		}
		public BorderTextView(Context context, AttributeSet attrs) {
			super(context, attrs);
		}
		private int sroke_width = 5;
		@Override
		protected void onDraw(Canvas canvas) {
			Paint paint = new Paint();
			paint.setStrokeWidth(7.5f);
	        //  将边框设为黑色
	        paint.setColor(android.graphics.Color.RED);
	        //  画TextView的4个边
	        canvas.drawLine(0, 0, this.getWidth() - sroke_width, 0, paint);
	        canvas.drawLine(0, 0, 0, this.getHeight() - sroke_width, paint);
	        canvas.drawLine(this.getWidth() - sroke_width, 0, this.getWidth() - sroke_width, this.getHeight() - sroke_width, paint);
	        canvas.drawLine(0, this.getHeight() - sroke_width, this.getWidth() - sroke_width, this.getHeight() - sroke_width, paint);
			super.onDraw(canvas);
		}

	
}
