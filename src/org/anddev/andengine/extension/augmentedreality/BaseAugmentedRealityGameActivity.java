package org.anddev.andengine.extension.augmentedreality;


import org.anddev.andengine.opengl.view.ComponentSizeChooser;
import org.anddev.andengine.opengl.view.RenderSurfaceView;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;

/**
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

		this.mCameraPreviewSurfaceView = new CameraPreviewSurfaceView(this);
		this.addContentView(this.mCameraPreviewSurfaceView, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		
//		this.mRenderSurfaceView.setZOrderMediaOverlay(true);
		this.mRenderSurfaceView.bringToFront();
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	
	protected void onSetContentView() {
		this.mRenderSurfaceView = new RenderSurfaceView(this, this.mEngine);	

		this.mRenderSurfaceView.setEGLConfigChooser(new ComponentSizeChooser(4, 4, 4, 4, 16, 0)); 
		this.mRenderSurfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);

		this.mRenderSurfaceView.applyRenderer();

		this.setContentView(this.mRenderSurfaceView, createSurfaceViewLayoutParams());
	}
	
	@Override
	protected void onPause() {
		super.onPause();
//		finish();
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}