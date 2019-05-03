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
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;


import static android.widget.Toast.LENGTH_SHORT;

public class Arview1 extends AppCompatActivity
{

    private ArFragment arFragment;
    static ModelRenderable houseRenderable,houseRenderable1,houseRenderable2,houseRenderable3,houseRenderable4, houseRenderable5, houseRenderable6;
    Button remove, help;
    HitResult hitResult = null;
    GridView gridView;
    static int pos;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar1);
        remove = findViewById(R.id.remove);
        help = findViewById(R.id.help);
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
                .setSource(this,R.raw.w1)
                .build()
                .thenAccept(renderable -> houseRenderable = renderable);

        ModelRenderable.builder()
                .setSource(this,R.raw.w2)
                .build()
                .thenAccept(renderable -> houseRenderable1 = renderable);

        ModelRenderable.builder()
                .setSource(this,R.raw.w3)
                .build()
                .thenAccept(renderable -> houseRenderable2 = renderable);

        ModelRenderable.builder()
                .setSource(this,R.raw.w4)
                .build()
                .thenAccept(renderable -> houseRenderable3 = renderable);

        ModelRenderable.builder()
                .setSource(this,R.raw.w5)
                .build()
                .thenAccept(renderable -> houseRenderable4 = renderable);

        ModelRenderable.builder()
                .setSource(this,R.raw.d1)
                .build()
                .thenAccept(renderable -> houseRenderable5 = renderable);

        ModelRenderable.builder()
                .setSource(this,R.raw.d3)
                .build()
                .thenAccept(renderable -> houseRenderable6 = renderable);


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
                    }
                    else if(pos==1)
                    {
                        house.setRenderable(houseRenderable1);
                        house.setParent(anchorNode);
                        house.select();
                    }
                    else if(pos==2)
                    {
                        house.setRenderable(houseRenderable2);
                        house.setParent(anchorNode);
                        house.select();
                    }
                    else if(pos==3)
                    {
                        house.setRenderable(houseRenderable3);
                        house.setParent(anchorNode);
                        house.select();
                    }
                    else if(pos==4)
                    {
                        house.setRenderable(houseRenderable4);
                        house.setParent(anchorNode);
                        house.select();
                    }
                    else if(pos==5)
                    {
                        house.setRenderable(houseRenderable5);
                        house.setParent(anchorNode);
                        house.select();
                    }
                    else if(pos==6)
                    {
                        house.setRenderable(houseRenderable6);
                        house.setParent(anchorNode);
                        house.select();
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



