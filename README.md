## COMPLETE
hibernate database layer
database creation
rest methods to accept form parameters and to generate an excel file
deployment of app-in-development to application servers

## TODO
automatic e-mail sender containing booking request details
add additional fields: comments, email, phone-number
html markup for space website to submit to
excel file format
upload to space engineers





## TECHNICAL NOTES
CustomerDao is a nice re-usable Dao interface(probably should just call it GenericDao)
CustomerDaoImpl.getSessionFactory shows how to integrate heroku's postgres along with your hibernate.cfg.xml

CustomerService class is weirdly required to prevent funky hibernate session already closed errors.