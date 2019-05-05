package com.example.wait;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import com.example.wait.MeasureDistance;

import static android.widget.Toast.LENGTH_SHORT;

public class Arview1 extends AppCompatActivity
{

    private ArFragment arFragment;
    static ModelRenderable houseRenderable;
    Button remove, help,btndst,clr;
    HitResult hitResult = null;
    GridView gridView;
    static int pos=0;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar1);
        remove = findViewById(R.id.remove);
        btndst = findViewById(R.id.btnDistance);
        clr = findViewById(R.id.clear);
        help = findViewById(R.id.help);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder
                        = new AlertDialog
                        .Builder(Arview1.this);
                builder.setMessage("1. Move your device slowly in a clockwise manner to detect any flat surface in front of you." +
                        "2. Click on any model from the list of items given at the bottom of the screen." +
                        "3. Tap on the detected plane to place the model." +
                        "4. Use your fingers to re-locate, resize or rotate the model." +
                        "5. To remove a model, tap on it and then click the remove button.");
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
                                        finish();
                                    }
                                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        gridView = (GridView)findViewById(R.id.asset_library);
        arFragment = (ArFragment)
                getSupportFragmentManager().findFragmentById(R.id.sceneform_fragment);
        createGrid();
        placeObjects();
    }
        private void placeObjects ()
        {
            arFragment.setOnTapArPlaneListener(
                    (HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {
                        houseRenderable = createRenderables();
                        Anchor anchor = hitResult.createAnchor();
                        AnchorNode anchorNode = new AnchorNode(anchor);
                        anchorNode.setParent(arFragment.getArSceneView().getScene());
                        TransformableNode house = new TransformableNode(arFragment.getTransformationSystem());

                        house.setRenderable(houseRenderable);
                        house.setParent(anchorNode);
                        house.select();

                        house.setOnTapListener(new Node.OnTapListener() {
                            @Override
                            public void onTap(HitTestResult hitTestResult, MotionEvent motionEvent) {
                                remove.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        anchorNode.removeChild(house);
                                    }
                                });
                            }
                        });
                    });
        }

        private ModelRenderable createRenderables ()
        {
            if (pos == 1) {
                ModelRenderable.builder()
                        .setSource(this, R.raw.w1)
                        .build()
                        .thenAccept(renderable -> houseRenderable = renderable);
            } else if (pos == 2) {
                ModelRenderable.builder()
                        .setSource(this, R.raw.w2)
                        .build()
                        .thenAccept(renderable -> houseRenderable = renderable);
            } else if (pos == 3) {
                ModelRenderable.builder()
                        .setSource(this, R.raw.w3)
                        .build()
                        .thenAccept(renderable -> houseRenderable = renderable);
            } else if (pos == 4) {
                ModelRenderable.builder()
                        .setSource(this, R.raw.w4)
                        .build()
                        .thenAccept(renderable -> houseRenderable = renderable);
            } else if (pos == 5) {
                ModelRenderable.builder()
                        .setSource(this, R.raw.w5)
                        .build()
                        .thenAccept(renderable -> houseRenderable = renderable);
            } else if (pos == 6) {
                ModelRenderable.builder()
                        .setSource(this, R.raw.d1)
                        .build()
                        .thenAccept(renderable -> houseRenderable = renderable);
            } else if (pos == 7) {
                ModelRenderable.builder()
                        .setSource(this, R.raw.d3)
                        .build()
                        .thenAccept(renderable -> houseRenderable = renderable);
            }
            return houseRenderable;
        }
    private void createGrid()
    {
        gridView.setAdapter(new Arview1.ImageAdapterGridView(this));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id) {
                pos = position;
                Toast.makeText(Arview1.this, "Selection item: " + pos, LENGTH_SHORT).show();
            }
        });
    }


    static class ImageAdapterGridView extends BaseAdapter {
        private Context mContext;
        Integer[] imageIDs = {
                R.drawable.w1,
                R.drawable.w2,
                R.drawable.w3,
                R.drawable.w4,
                R.drawable.w5,
                R.drawable.d1,
                R.drawable.d3
        };

        public ImageAdapterGridView(Context c) {
            mContext = c;
        }

        public int getCount() {
            return imageIDs.length;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView mImageView;

            if (convertView == null) {
                mImageView = new ImageView(mContext);
                mImageView.setLayoutParams(new GridView.LayoutParams(200, 200));
                mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                mImageView.setPadding(20, 0, 20, 0);
            } else {
                mImageView = (ImageView) convertView;
            }
            mImageView.setImageResource(imageIDs[position]);
            return mImageView;
        }
    }
}


