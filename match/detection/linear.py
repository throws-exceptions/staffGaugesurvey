import numpy as np


def linear(x,y):
    N = len(x)
    sumx = sum(x)
    sumy = sum(y)
    sumx2 = sum(c*c for c in x)
    sumxy = sum(np.array(x)*np.array(y))

    A = np.mat([[N,sumx],[sumx,sumx2]])
    B = np.array([sumy,sumxy])

    return np.linalg.solve(A,B)
