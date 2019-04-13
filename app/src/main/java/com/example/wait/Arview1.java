package com.example.wait;

import android.content.Context;
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
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import static android.widget.Toast.LENGTH_SHORT;

public class Arview1 extends AppCompatActivity {

    private ArFragment arFragment;
    ModelRenderable houseRenderable,houseRenderable1;
    Button remove, help, convert;
    HitResult hitResult = null;
    GridView gridView;
    int pos;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar1);

        remove = findViewById(R.id.remove);
        help = findViewById(R.id.help);
        convert = findViewById(R.id.convert);
        gridView = (GridView) findViewById(R.id.asset_library);
        gridView.setAdapter(new Arview1.ImageAdapterGridView(this));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id) {
                pos = position;
                Toast.makeText(Arview1.this, "Selection item: " + pos, LENGTH_SHORT).show();
            }
        });

        arFragment = (ArFragment)
                getSupportFragmentManager().findFragmentById(R.id.sceneform_fragment);

        ModelRenderable.builder()
                .setSource(this,R.raw.untitled1)
                .build()
                .thenAccept(renderable -> houseRenderable = renderable);

        ModelRenderable.builder()
                .setSource(this,R.raw.untitled)
                .build()
                .thenAccept(renderable -> houseRenderable1 = renderable);


        arFragment.setOnTapArPlaneListener(
                (HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {
                    Anchor anchor = hitResult.createAnchor();
                    AnchorNode anchorNode = new AnchorNode(anchor);
                    anchorNode.setParent(arFragment.getArSceneView().getScene());
                    TransformableNode house = new TransformableNode(arFragment.getTransformationSystem());

                    if (houseRenderable == null) {
                        return;
                    }
                    else if(pos==0)
                    {
                        house.setRenderable(houseRenderable);
                        house.setParent(anchorNode);
                        house.select();
                        //check this -> house.getLocalScale();
                                house.addTransformChangedListener(new Node.TransformChangedListener() {
                                    @Override
                                    public void onTransformChanged(Node node, Node node1) {
                                        //Vector3 position = anchorNode.getLocalPosition();
                                        //Toast.makeText(Arview1.this, "position:"+position, Toast.LENGTH_LONG).show();
                                        Vector3 position = node1.getWorldPosition();
                                        Toast.makeText(Arview1.this, "Position:"+position, Toast.LENGTH_LONG).show();
                                    }
                                });
                            }

                    else if(pos==1)
                    {
                        house.setRenderable(houseRenderable1);
                        house.setParent(anchorNode);
                        house.select();
                        convert.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Vector3 position = house.getLocalPosition();
                                Toast.makeText(Arview1.this, "position:"+position, Toast.LENGTH_LONG).show();
                            }
                        });
                    }
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

    static class ImageAdapterGridView extends BaseAdapter {
        private Context mContext;
        Integer[] imageIDs = {
                R.drawable.bricks,
                R.drawable.untitled131,

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

