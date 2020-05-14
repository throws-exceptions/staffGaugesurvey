import cv2
from detection.detect2 import detecion


def locate(Im, img):
    # Im = cv2.imread('F:/PyCharm 2019.3.3/projects/match/detection/IMG_1249.JPG')
    # img = cv2.imread('kedu.jpg')
    height = img.shape[0]
    width = img.shape[1]

    gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    ret, binary = cv2.threshold(gray, 127, 255, cv2.THRESH_BINARY)
    index = []
    d = {}
    x = {}
    y = {}
    w = {}
    h = {}

    contours, hierarchy = cv2.findContours(binary, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
    count = len(contours)
    for i in range(count):
        x[i], y[i], w[i], h[i] = cv2.boundingRect(contours[i])
        cv2.rectangle(binary, (x[i], y[i]), (x[i] + w[i], y[i] + h[i]), (0, 0, 255), 2)
    # cv2.namedWindow('1',cv2.WINDOW_NORMAL)
    # cv2.imshow('1',img)

    Z = detecion(Im)
    temp = Z[0] * width / 2 + Z[1]
    for i in range(height):
        if i > temp:
            img[i] = 0
    k = 0
    Y = []
    H = []
    for i in range(count):
        if (y[i] + h[i]) / 2 < temp and h[i] > 50 and w[i] > 50:
            fragment = gray[y[i]:y[i] + h[i], x[i]:x[i] + w[i]]
            # fra = Image.fromarray(cv2.cvtColor(fragment,cv2.COLOR_BGR2RGB))
            name = '/home/diamond/桌面/staffGaugesurvey/match/saveImage/' + 'Image' + str(k) + '.jpg'
            Y.append(y[i])
            H.append(h[i])
            k = k + 1
            cv2.imwrite(name, fragment)
            # fra.save(name,gray)

    # cv2.namedWindow('1',cv2.WINDOW_NORMAL)
    # cv2.imshow('1',img)

    # cv2.waitKey(0)

    return temp, Y, H, k
