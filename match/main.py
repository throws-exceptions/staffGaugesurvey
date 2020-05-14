import cv2
import numpy as np
from PIL import Image
from depth.deep import deep
import json
from test import Transmit
from test.ttypes import *
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol
from thrift.server import TServer
import socket
class TransmitHandler:
    def __int__(self):
        self.log = {}
    def sayMsg(self,msg):
        img = cv2.imread(msg)
        return deep(img)


if __name__ == '__main__':
    #img = cv2.imread('IMG_1249.JPG')
    #result = deep(img)
    #print('the result is '+str(result))
    handler = TransmitHandler()
    processor = Transmit.Processor(handler)
    transport = TSocket.TServerSocket('127.0.0.1', 8000)
    tfactory = TTransport.TBufferedTransportFactory()
    pfactory = TBinaryProtocol.TBinaryProtocolFactory()
    server = TServer.TSimpleServer(processor, transport, tfactory, pfactory)
    print('Starting python server...')
    server.serve()
    print('done')