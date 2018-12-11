# 效果展示
![](https://github.com/JadeKkang/floatingdemo/blob/master/image/floating.gif)
# 使用
    1.在项目gradle中添加
      allprojects {
         repositories {
             ...
             maven { url 'https://jitpack.io' }
         } 
      }
    2.添加依赖 
      {
        'com.github.JadeKkang:floatingdemo:v1.0.1'
      }
    3.xml中使用 
    <com.example.floating.FloatingIv 
        android:id="@+id/fi" 
        android:layout_width="100dp"
        android:layout_height="match_parent"
        android:layout_marginTop="20dp"
        android:layout_marginBottom="20dp"> 
    </com.example.floating.FloatingIv> 

# 预留方法

	1.setImg(int[] img)设置心形图片数组

	2.clickStart()可以外部按钮点击执行

# 注意
    1.效果是自己项目所需如不能满足要求，可自行扩展
