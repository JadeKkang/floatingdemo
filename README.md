# 效果展示
![](https://github.com/JadeKkang/floatingdemo/blob/master/image/floating.gif)
# 使用
1.在项目gradle中添加<br>  
allprojects {<br> 
repositories {<br> 
...<br> 
maven { url 'https://jitpack.io' }<br> 
}<br> 
}<br> 
2.添加依赖<br> 
 {'com.github.JadeKkang:floatingdemo:v1.0'}<br> 
3.xml中使用<br> 
<com.example.floating.FloatingIv<br> 
        android:id="@+id/fi"<br> 
        android:layout_width="100dp"<br> 
        android:layout_height="match_parent"<br> 
        android:layout_marginTop="20dp"<br> 
        android:layout_marginBottom="20dp"><br> 
    </com.example.floating.FloatingIv><br> 

# 预留方法

	1.setImg(int[] img)设置心形图片数组

	2.clickStart()可以外部按钮点击执行

# 注意
    1.效果是自己项目所需如不能满足要求，可自行扩展
