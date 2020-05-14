import cv2
from PIL import Image
from draft.Draft import DraftReading
from location.locate import locate
from recognize.recong import recongnize


def deep(img):
    # img = cv2.imread('IMG_1249.JPG')
    DraftReading(img)
    im = cv2.imread('/home/diamond/桌面/staffGaugesurvey/match/draft/kedu.jpg')
    depth = 0
    temp, Y, H, k = locate(img, im)
    num = []
    for i in range(k):
        name = '/home/diamond/桌面/staffGaugesurvey/match/saveImage/' + 'Image' + str(i) + '.jpg'
        image = Image.open(name)
        num.append(recongnize(image))
        if num[i] == 'M':
            break
    l = len(num)
    m = int(num[l - 2])
    m1 = int(num[0])
    m2 = 0.2 * (temp - (Y[0] + H[0] / 2)) / 150
    if m2 > 0.2:
        m2 = 0.2
    # print(m)
    # print(m1)
    # print(m2)
    for i in range(l):
        if i == 1:
            if num[i] == 'M':
                depth = format(m1 - m2, '.2f')
                # print('result is ' + str(depth))
            else:
                depth = format(m - 1 + 0.1 * m1 - m2, '.2f')
                # print('result is ' + str(depth))
    # depth = format(m - 1 + 0.1*m1 - m2,'.2f')
    # print('result is ' + str(depth))
    return depth
