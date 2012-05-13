package org.andengine.extension.augmentedreality;

import java.io.IOException;

import org.andengine.entity.scene.Scene;

/**
 * (c) Zynga 2012
 *
 * @author Nicolas Gramlich <ngramlich@zynga.com>
 * @since 14:43:33 - 12.05.2012
 */
public abstract class SimpleBaseAugmentedRealityGameActivity extends BaseAugmentedRealityGameActivity {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	protected abstract void onCreateResources() throws IOException;
	protected abstract Scene onCreateScene();

	@Override
	public final void onCreateResources(final OnCreateResourcesCallback pOnCreateResourcesCallback) throws IOException {
		this.onCreateResources();

		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public final void onCreateScene(final OnCreateSceneCallback pOnCreateSceneCallback) throws IOException {
		final Scene scene = this.onCreateScene();

		pOnCreateSceneCallback.onCreateSceneFinished(scene);
	}

	@Override
	public final void onPopulateScene(final Scene pScene, final OnPopulateSceneCallback pOnPopulateSceneCallback) throws IOException {
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
