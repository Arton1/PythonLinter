from abc import ABC
import math

y = 3

class Klasa(ABC):

	def __init__(self):
		xIsFive = self._hello(5)

	def _hello(self, x: int) -> bool:
		if x == 3:
			return True
		elif x == 3:
			y **= 3
		else:
			print(x)
		return False
