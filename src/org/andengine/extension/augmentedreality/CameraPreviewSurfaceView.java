package org.andengine.extension.augmentedreality;

import java.io.IOException;
import java.util.List;

import org.andengine.util.debug.Debug;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * (c) 2010 Nicolas Gramlich 
 * (c) 2011 Zynga Inc.
 * 
 * @author Nicolas Gramlich
 * @since 21:38:21 - 24.05.2010
 */
class CameraPreviewSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final SurfaceHolder mSurfaceHolder;
	private Camera mCamera;

	// ===========================================================
	// Constructors
	// ===========================================================

	public CameraPreviewSurfaceView(final Context pContext) {
		super(pContext);

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

	public void surfaceCreated(final SurfaceHolder pSurfaceHolder) {
		this.mCamera = Camera.open();
		try {
			this.mCamera.setPreviewDisplay(pSurfaceHolder);
		} catch (IOException e) {
			Debug.e("Error in Camera.setPreviewDisplay", e);
		}
	}

	public void surfaceDestroyed(final SurfaceHolder pSurfaceHolder) {
		this.mCamera.stopPreview();
		this.mCamera.release();
		this.mCamera = null;
	}

	public void surfaceChanged(final SurfaceHolder pSurfaceHolder, final int pPixelFormat, final int pWidth, final int pHeight) {
		Camera.Parameters params = this.mCamera.getParameters();
		List<Size> sizes = params.getSupportedPreviewSizes();
		Size optimalSize = getOptimalPreviewSize(sizes, pWidth, pHeight);
		params.setPreviewSize(optimalSize.width, optimalSize.height);
		this.mCamera.setParameters(params);
		this.mCamera.startPreview();
	}
	
	// ===========================================================
	// Methods
	// ===========================================================
	/**
	 * Method to get the optimal size for the camera preview
	 * @param sizes List of optimal sizes
	 * @param w width
	 * @param h height
	 * @return Size the optimal size for the surface view
	 */
	private Size getOptimalPreviewSize(List<Size> sizes, int w, int h) {
		final double ASPECT_TOLERANCE = 0.2;
		double targetRatio = (double) w / h;
		if (sizes == null)
			return null;

		Size optimalSize = null;
		double minDiff = Double.MAX_VALUE;

		int targetHeight = h;

		// Try to find an size match aspect ratio and size
		for (Size size : sizes) {
			Log.d("Augmented Reality", "Checking size " + size.width + "w " + size.height + "h");
			double ratio = (double) size.width / size.height;
			if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE)
				continue;
			if (Math.abs(size.height - targetHeight) < minDiff) {
				optimalSize = size;
				minDiff = Math.abs(size.height - targetHeight);
			}
		}

		// Cannot find the one match the aspect ratio, ignore the
		// requirement
		if (optimalSize == null) {
			minDiff = Double.MAX_VALUE;
			for (Size size : sizes) {
				if (Math.abs(size.height - targetHeight) < minDiff) {
					optimalSize = size;
					minDiff = Math.abs(size.height - targetHeight);
				}
			}
		}
		return optimalSize;
	}
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}