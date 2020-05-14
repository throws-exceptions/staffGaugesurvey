from test import Transmit
from test.constants import *
from test.ttypes import *
from thrift.protocol import TBinaryProtocol
from thrift.transport import TSocket
from thrift.transport import TTransport

transport = TSocket.TSocket('127.0.0.1', 8000)
transport = TTransport.TBufferedTransport(transport)
protocol = TBinaryProtocol.TBinaryProtocol(transport)
cilent = Transmit.Client(protocol)

transport.open()
msg = '/home/diamond/桌面/staffGaugesurvey/match/IMG_1249.JPG'
result = cilent.sayMsg(msg)

print('the result is ' + str(result))

transport.close()
