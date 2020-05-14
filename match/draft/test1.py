import cv2
from PIL import Image
import numpy as np

img = cv2.imread('IMG_1249.JPG')
height = img.shape[0]
width = img.shape[1]
img = img[0:height,int(0.25*width):int(0.75*width)]

se = cv2.getStructuringElement(cv2.MORPH_ELLIPSE,(55,55))
fo = cv2.morphologyEx(img,cv2.MORPH_OPEN,se)
f1 = cv2.subtract(img,fo)
gray = cv2.cvtColor(f1,cv2.COLOR_BGR2GRAY)
ret,binary = cv2.threshold(gray,55,255,cv2.THRESH_BINARY)
#kel = cv2.getStructuringElement(cv2.MORPH_ELLIPSE,(6,6))
#kel2 = cv2.getStructuringElement(cv2.MORPH_ELLIPSE,(1,1))
#dst = cv2.dilate(binary,kel)
#dst2 = cv2.erode(dst,kel2)
#cv2.imwrite('F:/PyCharm 2019.3.3/projects/match/draft/kedu2.jpg',dst)

for i in range(height):
    if i>0.9*height:
        binary[i] = 0
cv2.imwrite('F:/PyCharm 2019.3.3/projects/match/draft/kedu2.jpg',binary)


#cv2.imshow('1',binary)
#cv2.namedWindow('1',cv2.WINDOW_NORMAL)
#cv2.resizeWindow('1',2000,2000)
#cv2.waitKey(0)