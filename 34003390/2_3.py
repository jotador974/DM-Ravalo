# RAVALOMANDA
# JOAN
# 34003390
# L3 INFORMATIQUE
# Exercice 2.3
# coding: utf-8

from random import *
from tkinter import *
from threading import Thread, Condition, RLock
import time
from queue import *

class ThreadProducteur(Thread):
    def __init__(self,texte, q, tempsSommeil, nom):
        Thread.__init__(self)
        self.texte = Label(window, text="Ajout de l'entier: du Thread Producteur")
        self.message = texte
        self.q = q
        self.n=0
        self.tempsSommeil = tempsSommeil
        self.name = nom
        self.daemon = True


    def run(self):
        while True:
            self.ajout = randint(1,100)
            self.q.put(self.ajout, block=True,timeout=None)
            self.texte["text"] = " Ajout de l'entier: {}  du Thread Producteur".format(self.ajout)
            self.ajout=0
            self.texte.pack(expand=True, timeout=None)
            time.sleep(self.tempsSommeil)



class ThreadConsommateur(Thread):
    def __init__(self,texte, q, tempsSommeil, nom):
        Thread.__init__(self)
        self.texte = Label(window, text="Retrait de:  du Thread Consommateur!")
        self.message= texte
        self.q = q
        self.tempsSommeil = tempsSommeil
        self.name = nom
        self.daemon = True


    def run(self):
        while True:
            self.retrait = self.q.get(block=True, timeout=None)
            self.texte["text"] = "Retrait de: {} effectue du Thread Consommateur!".format(self.retrait)
            self.texte.pack(expand=True, fill='both')
            time.sleep(self.tempsSommeil)

# Fonction pour interrompre les threads avec un evenement

def Clavier(event):
    touche = event.keysym
    print(touche)
    if touche == 'Return':
        window.destroy() # Met fin au Thread

if __name__ == "__main__":
    
    # Création de l'entier partagé.
    q = Queue(maxsize=5)
    window = Tk()
    window.title('Opérations sur file')
    window.state('zoomed')
    window.bind("<Key>", Clavier)
    texte = Text(window, wrap=NONE)
    
    
    # Création et lancement des threads.
    p = ThreadProducteur(texte,q, 1, "p")
    c = ThreadConsommateur(texte,q, 1, "c")
    p.start()
    c.start()

    # démarrage du réceptionnaire d'évènements (boucle principale) :    
    # Attente frappe utilisateur pour stopper les threads.
    window.mainloop()
    
