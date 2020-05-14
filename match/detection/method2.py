import cv2
import numpy as np
import matplotlib.pyplot as plt
from detection.linear import *
img = cv2.imread('IMG_1249.JPG')
height = img.shape[0]
width = img.shape[1]
ret,binary = cv2.threshold(img,100,255,cv2.THRESH_BINARY)
img4 = cv2.Canny(binary,20,30)
for i in range(height):
    if i<0.8*height:
        img4[i] = 0
x= []
y = []
k = 0
for i in range(height):
    for j in range(width):
        if img4[i,j] == 255:
            x.append(i)
            y.append(j)
            k = k + 1

#print(height)
X = np.array(x)
Y = np.array(y)

Z = np.polyfit(Y,X,1)
P = np.poly1d(Z)

X1 = np.arange(width)
Y1 = P(X1)
plot = plt.plot(X1,Y1,'r')
plt.axis([0,width,0,height])
ax = plt.gca()
ax.xaxis.set_ticks_position('top')
ax.invert_yaxis()

origin = plt.imread('IMG_1249.JPG')
#cv2.namedWindow('1',cv2.WINDOW_NORMAL)
#cv2.imshow('1',img4)
plt.imshow(origin)
plt.show()
cv2.waitKey(0)