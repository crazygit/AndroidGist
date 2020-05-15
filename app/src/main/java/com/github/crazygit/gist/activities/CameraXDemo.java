package com.github.crazygit.gist.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageAnalysisConfig;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureConfig;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.core.app.ActivityCompat;

import com.github.crazygit.gist.base.BaseActivity;
import com.github.crazygit.gist.databinding.ActivityCameraXDemoBinding;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// 参考
// https://codelabs.developers.google.com/codelabs/camerax-getting-started
public class CameraXDemo extends BaseActivity<ActivityCameraXDemoBinding> {

    private static final int REQUEST_CODE_PERMISSIONS = 557;
    private static final String[] REQUIRED_PERMISSIONS = {Manifest.permission.CAMERA};
    private ExecutorService executor = Executors.newSingleThreadExecutor();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        dataBinding = ActivityCameraXDemoBinding.inflate(getLayoutInflater());
        setContentView(dataBinding.getRoot());

        // Request camera permissions
        if (allPermissionsGranted()) {
            // Instead of calling `startCamera()` on the main thread,
            // we use `viewFinder.post { ... }` to make sure that `viewFinder` has already been inflated into the view
            // when `startCamera()` is called.
            dataBinding.viewFinder.post(this::startCamera);
        } else {
            ActivityCompat.requestPermissions(
                    this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }
        // Every time the provided texture view changes, recompute layout
        dataBinding.viewFinder.addOnLayoutChangeListener(
                (v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> updateTransform()
        );

    }

    private void startCamera() {
        // TODO: Implement CameraX operations
        // Create configuration object for the viewfinder use case
        PreviewConfig previewConfig = new PreviewConfig.Builder().setTargetResolution(new Size(640, 480)).build();
        Preview preview = new Preview(previewConfig);
        preview.setOnPreviewOutputUpdateListener(output -> {
            ViewGroup parent = (ViewGroup) dataBinding.viewFinder.getParent();
            parent.removeView(dataBinding.viewFinder);
            parent.addView(dataBinding.viewFinder, 0);
            dataBinding.viewFinder.setSurfaceTexture(output.getSurfaceTexture());
            updateTransform();
        });


        // Create configuration object for the image capture use case
        // We don't set a resolution for image capture; instead, we
        // select a capture mode which will infer the appropriate
        // resolution based on aspect ration and requested mode
        ImageCaptureConfig imageCaptureConfig = new ImageCaptureConfig.Builder().setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY).build();

        // Build the image capture use case and attach button click listener
        ImageCapture imageCapture = new ImageCapture(imageCaptureConfig);
        dataBinding.captureButton.setOnClickListener(v -> {
            File file = new File(getExternalMediaDirs()[0], System.currentTimeMillis() + ".jpg");
            imageCapture.takePicture(file, executor, new ImageCapture.OnImageSavedListener() {
                @Override
                public void onImageSaved(@NonNull File file) {
                    String msg = "Photo capture succeeded: " + file.getAbsolutePath();
                    Log.d("CameraXApp", msg);
                    dataBinding.viewFinder.post(
                            () -> Toast.makeText(CameraXDemo.this, msg, Toast.LENGTH_SHORT).show()
                    );
                }

                @Override
                public void onError(@NonNull ImageCapture.ImageCaptureError
                                            imageCaptureError, @NonNull String message, @Nullable Throwable cause) {
                    String msg = "Photo capture failed: " + message;
                    Log.e("CameraXApp", msg, cause);
                    dataBinding.viewFinder.post(
                            () -> Toast.makeText(CameraXDemo.this, msg, Toast.LENGTH_SHORT).show()
                    );
                }
            });
        });


        // Setup image analysis pipeline that computes average pixel luminance
        ImageAnalysisConfig analyzerConfig = new ImageAnalysisConfig.Builder().setImageReaderMode(
                ImageAnalysis.ImageReaderMode.ACQUIRE_LATEST_IMAGE).build();
        // Build the image analysis use case and instantiate our analyzer
        ImageAnalysis analyzerUseCase = new ImageAnalysis(analyzerConfig);
        analyzerUseCase.setAnalyzer(executor, new LuminosityAnalyzer());

        CameraX.bindToLifecycle(this, preview, imageCapture, analyzerUseCase);
    }

    private void updateTransform() {
        // TODO: Implement camera viewfinder transformations
        Matrix matrix = new Matrix();
        float centerX = dataBinding.viewFinder.getWidth() / 2f;
        float centerY = dataBinding.viewFinder.getHeight() / 2f;
        int rotation = dataBinding.viewFinder.getDisplay().getRotation();
        int rotationDegrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                rotationDegrees = 0;
                break;
            case Surface.ROTATION_90:
                rotationDegrees = 90;
                break;
            case Surface.ROTATION_180:
                rotationDegrees = 180;
                break;
            case Surface.ROTATION_270:
                rotationDegrees = 270;
                break;
            default:
        }
        matrix.postRotate(-rotationDegrees, centerX, centerY);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                dataBinding.viewFinder.post(this::startCamera);
            } else {
                Toast.makeText(this,
                        "Permissions not granted by the user.",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private boolean allPermissionsGranted() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    private class LuminosityAnalyzer implements ImageAnalysis.Analyzer {
        private long lastAnalyzedTimestamp = 0L;

        @Override
        public void analyze(ImageProxy image, int rotationDegrees) {
            long currentTimestamp = System.currentTimeMillis();
            // Calculate the average luma no more often than every second
            if (currentTimestamp - lastAnalyzedTimestamp >=
                    TimeUnit.SECONDS.toMillis(1)) {
                // Since format in ImageAnalysis is YUV, image.planes[0]
                // contains the Y (luminance) plane
                ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                // Extract image data from callback object
                buffer.rewind();
                byte[] data = new byte[buffer.remaining()];
                buffer.get(data);
                // Convert the data into an array of pixel values
                int[] pixels = new int[data.length];
                for (int i = 0; i < data.length; i++) {
                    pixels[i] = data[i] & 0xFF;
                }
                String luma = Arrays.stream(pixels).average().toString();
                // Log the new luma value
                Log.d("CameraXApp", "Average luminosity: " + luma);
                // Update timestamp of last analyzed frame
                lastAnalyzedTimestamp = currentTimestamp;
            }

        }
    }
}
