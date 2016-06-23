# AddwordLib([中文文档](https://github.com/jinguangyue/AddwordLib/blob/master/README_CN.md))
TextView add text , vertical discharge switching (the direction of the font is no change!)

If an ordinary Textview font rotation direction , 
then direction must be changed, but here I customized a view to achieve this function .

#Features([DownLoad APK](https://github.com/jinguangyue/AddwordLib/blob/master/apk/demo.apk?raw=true))

* The Text , horizontal, and vertical text direction unchanged when switching to
* can move one finger , one finger zoom
* Two-finger rotating zoom
* add multiple
* Support the text centered Left Right
* Automatically adapt to the border when entering text

#Include library

Edit your build.gradle file and add below dependency:

```
dependencies {
    compile 'com.jinguangyue.addwordlib:addwordLib:1.0.0'
}
```

# The Renderings
![](https://github.com/jinguangyue/AddwordLib/blob/master/AddwordLib/screenshots/textview%E6%A8%AA%E7%AB%96.gif)


# Use it

**Add View**
Here you can add more than one view, and you can move and resize. You Can do this for Add view:

```
private void addMyFrame() {
        //Each additional view Now  set all view is not selected
        for (int i = (addFrameHolders.size() - 1); i >= 0; i--) {
            AddWordFrame addWordFrame = addFrameHolders.get(i).getAddWordFrame();
            if (addWordFrame.isSelect()) {
                addWordFrame.setSelect(false);
                break;
            }
        }

        //new a view
        addWordFrame = new AddWordFrame(this);
        addWordFrame.setSelect(true);
        //add to your frame
        frame.addView(addWordFrame);

        layout = addWordFrame.getLayout();

        addWordBitmap = BitmapUtils.convertViewToBitmap(layout);

        addWordWidth = addWordBitmap.getWidth();
        addWordHeight = addWordBitmap.getHeight();

        //Set to the center of the screen and set the vertical and horizontal coordinates of four points
        addWordx1 = width/2 - addWordWidth /2;
        addWordy1 = height/3;
        addWordFrame.leftTop.set(addWordx1, addWordy1);
        addWordFrame.rightTop.set(addWordx1 + addWordWidth, addWordy1);
        addWordFrame.leftBottom.set(addWordx1, addWordy1 + addWordHeight);
        addWordFrame.rightBottom.set(addWordx1 + addWordWidth, addWordy1 + addWordHeight);


        //here use matrix to scaling gesture
        addWordMatrix = new Matrix();
        addWordMatrix.postTranslate(addWordx1, addWordy1);
        addWordFrame.setMatrix(addWordMatrix);

        //Here for each view with a rectangular package , click the rectangle on selected current view
        AddWordFrameState addWordFrameState = new AddWordFrameState();
        addWordFrameState.setLeft(addWordx1);
        addWordFrameState.setTop(addWordy1);
        addWordFrameState.setRight(addWordx1 + addWordWidth);
        addWordFrameState.setBottom(addWordy1 + addWordHeight);

        AddFrameHolder addFrameHolder = new AddFrameHolder();
        addFrameHolder.setAddWordFrame(addWordFrame);
        addFrameHolder.setState(addWordFrameState);
        addFrameHolders.add(addFrameHolder);

        addWordFrame.setOnTouchListener(new AddWordMyOntouch());
        AddWordSelectImageCount = addFrameHolders.size() - 1;
    }
```

**Control select which View**

```
private void selectMyFrame(float x, float y) {
        //Select the option to cancel all back to only one click is selected
        for (int i = (addFrameHolders.size() - 1); i >= 0; i--) {
            AddFrameHolder addFrameHolder = addFrameHolders.get(i);
            if (addFrameHolder.getAddWordFrame().isSelect()) {
                addFrameHolder.getAddWordFrame().setSelect(false);
                break;
            }
        }

        for (int i = (addFrameHolders.size() - 1); i >= 0; i--) {
            AddFrameHolder addFrameHolder = addFrameHolders.get(i);
            //Create a rectangular area here getLeft getTop etc. mean the current view of the leftmost
            // uppermost and lowermost rightmost only to click inside the region is selected
            Rect rect = new Rect((int)addFrameHolder.getState().getLeft(),
                    (int)addFrameHolder.getState().getTop(),
                    (int)addFrameHolder.getState().getRight(),
                    (int)addFrameHolder.getState().getBottom());

            if (rect.contains((int) x, (int) y)) {
                //If you select the current view mentioned uppermost layer
                addFrameHolder.getAddWordFrame().bringToFront();
                addFrameHolder.getAddWordFrame().setSelect(true);
                //Which record is selected
                AddWordSelectImageCount = i;
                LogUtils.e("selected");
                break;
            }
            AddWordSelectImageCount = -1;
            LogUtils.e("no select");
        }
    }
```

**Demo detailed code can be viewed below MainActivity**

#License
```
Copyright 2016 jinguangyue

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```