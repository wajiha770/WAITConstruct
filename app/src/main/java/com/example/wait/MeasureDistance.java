package com.example.wait;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Pose;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.Camera;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.Sun;
import com.google.ar.sceneform.collision.Box;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.MaterialFactory;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ShapeFactory;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.ArrayList;
import java.util.List;

public class MeasureDistance extends AppCompatActivity implements Node.OnTapListener{

    ArrayList<Float> List1 = new ArrayList<>();
    ArrayList<Float> List2 = new ArrayList<>();
    private ArFragment arFragment;
    private AnchorNode lastAnchorNode;
    private TextView distance;
    Button calDistance, clear, help;
    ModelRenderable cubeRenderable;
    boolean btnLengthClicked;
    Vector3 point1, point2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_measuredistance);
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);
        distance = findViewById(R.id.txtDistance);
        calDistance = findViewById(R.id.btnDistance);

        calDistance.setOnClickListener(v -> {
            btnLengthClicked = true;
            onClear();
        });
        clear = findViewById(R.id.clear);
        clear.setOnClickListener(v -> onClear());

        help=findViewById(R.id.help);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder
                        = new AlertDialog
                        .Builder(MeasureDistance.this);
                builder.setMessage("1. Tap on one end of your plot to place an anchor." +
                        "2. Move to the other end and tap again to place another anchor." +
                        "3. You will see the distance between the points in meters at the bottom of the screen.");
                builder.setTitle("Instructions");
                builder.setCancelable(false);
                builder
                        .setPositiveButton(
                                "Ok",
                                new DialogInterface
                                        .OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which)
                                    {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        MaterialFactory.makeTransparentWithColor(this, new Color(0F, 0F, 244F))
                .thenAccept(
                        material -> {
                            Vector3 vector3 = new Vector3(0.01f, 0.01f, 0.01f);
                            cubeRenderable = ShapeFactory.makeCube(vector3, Vector3.zero(), material);
                            cubeRenderable.setShadowCaster(false);
                            cubeRenderable.setShadowReceiver(false);
                        });


        arFragment.setOnTapArPlaneListener(
                (HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {
                    if (cubeRenderable == null) {
                        return;
                    }

                    if (btnLengthClicked) {
                        if (lastAnchorNode == null) {
                            Anchor anchor = hitResult.createAnchor();
                            AnchorNode anchorNode = new AnchorNode(anchor);
                            anchorNode.setParent(arFragment.getArSceneView().getScene());

                            Pose pose = anchor.getPose();
                            if (List1.isEmpty()) {
                                List1.add(pose.tx());
                                List1.add(pose.ty());
                                List1.add(pose.tz());
                            }
                            TransformableNode transformableNode = new TransformableNode(arFragment.getTransformationSystem());
                            transformableNode.setParent(anchorNode);
                            transformableNode.setRenderable(cubeRenderable);
                            transformableNode.select();
                            lastAnchorNode = anchorNode;
                        } else {
                            int val = motionEvent.getActionMasked();
                            float axisVal = motionEvent.getAxisValue(MotionEvent.AXIS_X, motionEvent.getPointerId(motionEvent.getPointerCount() - 1));
                            Log.e("Values:", String.valueOf(val) + String.valueOf(axisVal));
                            Anchor anchor = hitResult.createAnchor();
                            AnchorNode anchorNode = new AnchorNode(anchor);
                            anchorNode.setParent(arFragment.getArSceneView().getScene());

                            Pose pose = anchor.getPose();


                            if (List2.isEmpty()) {
                                List2.add(pose.tx());
                                List2.add(pose.ty());
                                List2.add(pose.tz());
                                float d = getDistanceMeters(List1, List2);
                                distance.setText("Distance: " + String.valueOf(d));
                            } else {
                                List1.clear();
                                List1.addAll(List2);
                                List2.clear();
                                List2.add(pose.tx());
                                List2.add(pose.ty());
                                List2.add(pose.tz());
                                float d = getDistanceMeters(List1, List2);
                                distance.setText("Distance: " + String.valueOf(d)+" meters");
                            }

                            TransformableNode transformableNode = new TransformableNode(arFragment.getTransformationSystem());
                            transformableNode.setParent(anchorNode);
                            transformableNode.setRenderable(cubeRenderable);
                            transformableNode.select();

                            Vector3 point1, point2;
                            point1 = lastAnchorNode.getWorldPosition();
                            point2 = anchorNode.getWorldPosition();

                            final Vector3 difference = Vector3.subtract(point1, point2);
                            final Vector3 directionFromTopToBottom = difference.normalized();
                            final Quaternion rotationFromAToB =
                                    Quaternion.lookRotation(directionFromTopToBottom, Vector3.up());
                            MaterialFactory.makeOpaqueWithColor(getApplicationContext(), new Color(0, 255, 244))
                                    .thenAccept(
                                            material -> {
                                                ModelRenderable model = ShapeFactory.makeCube(
                                                        new Vector3(.01f, .01f, difference.length()),
                                                        Vector3.zero(), material);
                                                Node node = new Node();
                                                node.setParent(anchorNode);
                                                node.setRenderable(model);
                                                node.setWorldPosition(Vector3.add(point1, point2).scaled(.5f));
                                                node.setWorldRotation(rotationFromAToB);
                                            }
                                    );
                            lastAnchorNode = anchorNode;
                        }
                    }
                });

    }

    private void onClear() {
        List<Node> children = new ArrayList<>(arFragment.getArSceneView().getScene().getChildren());
        for (Node node : children) {
            if (node instanceof AnchorNode) {
                if (((AnchorNode) node).getAnchor() != null) {
                    ((AnchorNode) node).getAnchor().detach();
                }
            }
            if (!(node instanceof Camera) && !(node instanceof Sun)) {
                node.setParent(null);
            }
        }
        List1.clear();
        List2.clear();
        lastAnchorNode = null;
        point1 = null;
        point2 = null;
        distance.setText("");
    }

    private float getDistanceMeters(ArrayList<Float> arayList1, ArrayList<Float> arrayList2) {

        float distanceX = arayList1.get(0) - arrayList2.get(0);
        float distanceY = arayList1.get(1) - arrayList2.get(1);
        float distanceZ = arayList1.get(2) - arrayList2.get(2);
        return (float) Math.sqrt(distanceX * distanceX +
                distanceY * distanceY +
                distanceZ * distanceZ);
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onTap(HitTestResult hitTestResult, MotionEvent motionEvent) {
        Node node = hitTestResult.getNode();
        Box box = (Box) node.getRenderable().getCollisionShape();
        assert box != null;
        Vector3 renderableSize = box.getSize();
        Vector3 transformableNodeScale = node.getWorldScale();
        Vector3 finalSize =
                new Vector3(
                        renderableSize.x * transformableNodeScale.x,
                        renderableSize.y * transformableNodeScale.y,
                        renderableSize.z * transformableNodeScale.z);
        distance.setText("Height: " + String.valueOf(finalSize.y));
        Log.e("FinalSize: ", String.valueOf(finalSize.x + " " + finalSize.y + " " + finalSize.z));
        //Toast.makeText(this, "Final Size is " + String.valueOf(finalSize.x + " " + finalSize.y + " " + finalSize.z), Toast.LENGTH_SHORT).show();
    }
}