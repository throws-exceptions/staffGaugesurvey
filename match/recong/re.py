import cv2
import numpy as np
import os
from PIL import Image


#def recongnize(testImage):
path = "F:/模式识别/matchphoto"
filelist = os.listdir(path)
testImage = Image.open("F:/PyCharm 2019.3.3/projects/match/saveImage/Image2.jpg")
testImage = testImage.resize((64, 64))
mat = np.matrix(testImage)
num = len(filelist)
im3 = np.zeros((64, 64))
count = np.zeros(num)

def pre(ima):
    im = np.array(ima);
    for i in range(im.shape[0]):
        for j in range(im.shape[1]):
            if im[i, j] > 200:
                im[i, j] = 1;
            else:
                im[i, j] = 0;
    return im

im1 = pre(mat)
for i in range(num):
    I = filelist[i]
    I1 = Image.open(path + '/' + str(I))
    I1 = I1.resize((64, 64))
    mat2 = np.matrix(I1)

    im2 = pre(mat2)
    for j in range(64):
        for k in range(64):
            im3[j, k] = im1[j, k] & im2[j, k]
            if im3[j, k] == 1:
                count[i] = count[i] + 1

for i in range(num):
    max = np.argmax(count)

print(filelist[max][:1])