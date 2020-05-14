import cv2
import numpy as np
from PIL import Image
img = Image.open('IMG_1249.JPG')
r,g,b = img.split()
r = np.array(np.matrix(r))
g = np.array(np.matrix(g))
b = np.array(np.matrix(b))
img2 = img.copy()
r2,g2,b2 = img.split()
r2 = np.array(np.matrix(r))
g2 = np.array(np.matrix(g))
b2 = np.array(np.matrix(b))
(m,n) = img.size
level = 40
level2 = 55

for i in range(m):
    for j in range(n):
        if int(r[i,j])-int(g[i,j])>level&int(r[i,j])-int(b[i,j])>level&int(r[i,j])-int(g[i,j])<level2&int(r[i,j])-int(b[i,j])<level2:
            r2[i,j] = r[i,j]
            g2[i,j] = g[i,j]
            b2[i,j] = b[i,j]
        else:
            r2[i,j] = 255
            g2[i,j] = 255
            b2[i,j] = 255
r2 = Image.fromarray(np.matrix(r2))
g2 = Image.fromarray(np.matrix(g2))
b2 = Image.fromarray(np.matrix(b2))

new_rgb = [r2,g2,b2]
img3 = Image.merge('RGB',new_rgb)
img3.show()