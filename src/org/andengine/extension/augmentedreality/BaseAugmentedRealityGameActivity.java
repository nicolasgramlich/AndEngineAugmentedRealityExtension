package org.andengine.extension.augmentedreality;

import org.andengine.opengl.view.RenderSurfaceView;
import org.andengine.ui.activity.BaseGameActivity;

import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

/**
 * (c) 2010 Nicolas Gramlich (c) 2011 Zynga Inc.
 * 
 * @author Nicolas Gramlich
 * @since 21:38:32 - 24.05.2010
 */
public abstract class BaseAugmentedRealityGameActivity extends BaseGameActivity {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private CameraPreviewSurfaceView mCameraPreviewSurfaceView;

	// ===========================================================
	// Constructors
	// ===========================================================

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	protected void onSetContentView() {
		final FrameLayout frameLayout = new FrameLayout(this);
		final FrameLayout.LayoutParams frameLayoutLayoutParams = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.MATCH_PARENT);

		this.mCameraPreviewSurfaceView = new CameraPreviewSurfaceView(this);

		final FrameLayout.LayoutParams camViewLayoutParams = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.WRAP_CONTENT,
				FrameLayout.LayoutParams.WRAP_CONTENT);

		this.mRenderSurfaceView = new RenderSurfaceView(this);
		this.mRenderSurfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
		this.mRenderSurfaceView.setZOrderMediaOverlay(true);
		this.mRenderSurfaceView.setRenderer(this.mEngine, this);

		final android.widget.FrameLayout.LayoutParams surfaceViewLayoutParams = new FrameLayout.LayoutParams(
				super.createSurfaceViewLayoutParams());
		surfaceViewLayoutParams.gravity = Gravity.CENTER;

		frameLayout.addView(this.mCameraPreviewSurfaceView, camViewLayoutParams);
		frameLayout.addView(this.mRenderSurfaceView, surfaceViewLayoutParams);

		this.setContentView(frameLayout, frameLayoutLayoutParams);
	}

	@Override
	protected void onPause() {
		super.onPause();
		// finish();
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}