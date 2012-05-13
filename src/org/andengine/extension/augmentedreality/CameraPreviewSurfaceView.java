package org.andengine.extension.augmentedreality;

import java.io.IOException;
import java.util.List;

import org.andengine.engine.options.resolutionpolicy.IResolutionPolicy;
import org.andengine.util.debug.Debug;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * (c) 2010 Nicolas Gramlich 
 * (c) 2011 Zynga Inc.
 * 
 * @author Nicolas Gramlich
 * @since 21:38:21 - 24.05.2010
 */
class CameraPreviewSurfaceView extends SurfaceView implements SurfaceHolder.Callback, IResolutionPolicy.Callback {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final IResolutionPolicy mResolutionPolicy;
	private final SurfaceHolder mSurfaceHolder;
	private Camera mCamera;

	// ===========================================================
	// Constructors
	// ===========================================================

	public CameraPreviewSurfaceView(final Context pContext, final IResolutionPolicy pResolutionPolicy) {
		super(pContext);

		this.mResolutionPolicy = pResolutionPolicy;

		this.mSurfaceHolder = this.getHolder();
		this.mSurfaceHolder.addCallback(this);
		this.mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}
	
	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	/**
	 * @see android.view.View#measure(int, int)
	 */
	@Override
	protected void onMeasure(final int pWidthMeasureSpec, final int pHeightMeasureSpec) {
		this.mResolutionPolicy.onMeasure(this, pWidthMeasureSpec, pHeightMeasureSpec);
	}

	@Override
	public void onResolutionChanged(final int pWidth, final int pHeight) {
		this.setMeasuredDimension(pWidth, pHeight);
	}

	@Override
	public void surfaceCreated(final SurfaceHolder pSurfaceHolder) {
		this.mCamera = Camera.open();
		try {
			this.mCamera.setPreviewDisplay(pSurfaceHolder);
		} catch (final IOException e) {
			Debug.e(e);
		}
	}

	@Override
	public void surfaceDestroyed(final SurfaceHolder pSurfaceHolder) {
		this.mCamera.stopPreview();
		this.mCamera.release();
		this.mCamera = null;
	}

	@Override
	public void surfaceChanged(final SurfaceHolder pSurfaceHolder, final int pPixelFormat, final int pWidth, final int pHeight) {
		final Parameters parameters = this.mCamera.getParameters();
		final List<Size> previewSizes = parameters.getSupportedPreviewSizes();

		// TODO Choose appropriate preview size. (Maybe try to get it as close to pWidth/pHeight as possible?)
		final Size previewSize = previewSizes.get(0);

		parameters.setPreviewSize(previewSize.width, previewSize.height);

		this.mCamera.setParameters(parameters);
		this.mCamera.startPreview();
	}
	
	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}