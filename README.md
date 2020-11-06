Automatyczna budowa obrazu nastepuje w dwoch przypadkach:
- wykonaniu zmian bezposrednio w obrazie i wrzuceniu ich do reposytorium docker hub za pomoca polecenia: docker push mati96/health-tracker:tagname
- wykonaniu zmian w kodzie zrodlowym projektu i wrzuceniu ich do repozytorium git za pomoca polecenia: git push origin master (po uprzednim zrobieniu commita). Docker hub automatycznie wykryje zmiany i przebuduje obraz.
Pobranie obrazu na lokalna maszyne mozemy wykonac na dwa sposoby:
- wykorzystujac repozytorium docker hub: docker pull mati96/health-tracker
- uzywajac bezposrednio repozytorium github'owego: docker build https://github.com/pomykalskimateusz/health-tracker.git