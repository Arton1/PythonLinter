from abc import ABC
import math

y = 3

class Klasa(ABC):

	def __init__(self):
		xIsFive = self._hello(5)
		self.print(self.value)

	def funkcja(self, x: int) -> bool:
		if x == 1:
			print(x)
		elif x == 2:
			x += 2
		else:
			return x == 1
		return True

	def wywolanie0123(self) -> float:
		self.funkcja(1)