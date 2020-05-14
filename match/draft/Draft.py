import cv2


def DraftReading(img):
    # img = cv2.imread('IMG_1044.JPG')
    height = img.shape[0]
    width = img.shape[1]
    img = img[0:height, int(0.25 * width):int(0.75 * width)]

    se = cv2.getStructuringElement(cv2.MORPH_ELLIPSE, (55, 55))
    fo = cv2.morphologyEx(img, cv2.MORPH_OPEN, se)
    f1 = cv2.subtract(img, fo)
    gray = cv2.cvtColor(f1, cv2.COLOR_BGR2GRAY)
    ret, binary = cv2.threshold(gray, 55, 255, cv2.THRESH_BINARY)
    for i in range(height):
        if i > 0.9 * height:
            binary[i] = 0

    cv2.imwrite('/home/diamond/桌面/staffGaugesurvey/match/draft/kedu.jpg', binary)

# cv2.imshow('1',img)
# cv2.namedWindow('1',cv2.WINDOW_NORMAL)
# cv2.resizeWindow('1',1000,1000)
# cv2.waitKey(0)
