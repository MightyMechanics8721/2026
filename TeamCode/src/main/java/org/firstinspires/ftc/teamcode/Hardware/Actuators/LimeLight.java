package org.firstinspires.ftc.teamcode.Hardware.Actuators;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Limelight 3A AprilTag helper for FTC.
 *
 * Distance is measured from the camera to the AprilTag,
 * not from the robot's center to the AprilTag.
 */
public class LimeLight {

    private final Limelight3A limelight;

    private LLResult latestResult;
    private List<LLResultTypes.FiducialResult> detectedTags =
            Collections.emptyList();

    private static final double METERS_TO_INCHES = 39.37007874;

    public LimeLight(Limelight3A limelight, int pipeline) {
        this.limelight = limelight;
        setPipeline(pipeline);
    }

    /**
     * Start polling for Limelight results.
     * Call this once during OpMode initialization.
     */
    public void start() {
        limelight.start();
    }

    /**
     * Select a pipeline by its index.
     */
    public void setPipeline(int pipelineIndex) {
        limelight.pipelineSwitch(pipelineIndex);
    }

    /**
     * Update the cached results.
     * Call this once per loop before reading detections.
     */
    public void update() {
        latestResult = limelight.getLatestResult();

        if (latestResult != null
                && latestResult.isValid()
                && latestResult.getFiducialResults() != null) {

            detectedTags = new ArrayList<>(
                    latestResult.getFiducialResults()
            );

        } else {
            detectedTags = Collections.emptyList();
        }
    }

    /**
     * Returns true if the current frame contains at least one AprilTag.
     */
    public boolean hasTarget() {
        return !detectedTags.isEmpty();
    }

    /**
     * Returns all AprilTags detected in the latest updated frame.
     */
    public List<LLResultTypes.FiducialResult> getDetectedTags() {
        return detectedTags;
    }

    /**
     * Returns all detected AprilTag IDs.
     */
    public List<Integer> getDetectedTagIds() {
        List<Integer> ids = new ArrayList<>();

        for (LLResultTypes.FiducialResult tag : detectedTags) {
            ids.add(tag.getFiducialId());
        }

        return ids;
    }

    /**
     * Checks whether a particular AprilTag ID is visible.
     */
    public boolean isTagVisible(int id) {
        return getTag(id) != null;
    }

    /**
     * Returns the result for a particular AprilTag ID.
     * Returns null if that tag is not visible.
     */
    public LLResultTypes.FiducialResult getTag(int id) {
        for (LLResultTypes.FiducialResult tag : detectedTags) {
            if (tag.getFiducialId() == id) {
                return tag;
            }
        }

        return null;
    }

    /**
     * Returns the distance from the camera to the specified tag,
     * in inches.
     *
     * Uses the 3D camera-space translation:
     * distance = sqrt(x^2 + y^2 + z^2).
     *
     * Returns NaN if the tag or its pose is unavailable.
     */
    public double getDistanceInches(int id) {
        LLResultTypes.FiducialResult tag = getTag(id);

        if (tag == null) {
            return Double.NaN;
        }

        Pose3D pose = tag.getTargetPoseCameraSpace();

        if (pose == null || pose.getPosition() == null) {
            return Double.NaN;
        }

        double x = pose.getPosition().x;
        double y = pose.getPosition().y;
        double z = pose.getPosition().z;

        double distanceMeters = Math.sqrt(
                x * x + y * y + z * z
        );

        return distanceMeters * METERS_TO_INCHES;
    }

    /**
     * Returns the horizontal targeting angle in degrees.
     * This is relative to the Limelight's targeting crosshair.
     */
    public double getTx(int id) {
        LLResultTypes.FiducialResult tag = getTag(id);

        if (tag == null) {
            return Double.NaN;
        }

        return tag.getTargetXDegrees();
    }

    /**
     * Returns the vertical targeting angle in degrees.
     */
    public double getTy(int id) {
        LLResultTypes.FiducialResult tag = getTag(id);

        if (tag == null) {
            return Double.NaN;
        }

        return tag.getTargetYDegrees();
    }

    /**
     * Returns the most recently received Limelight result.
     */
    public LLResult getLatestResult() {
        return latestResult;
    }

    /**
     * Stop polling when the OpMode ends.
     */
    public void stop() {
        limelight.stop();
    }
}